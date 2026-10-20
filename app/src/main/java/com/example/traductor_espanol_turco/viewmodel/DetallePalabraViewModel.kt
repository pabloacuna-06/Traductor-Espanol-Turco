package com.example.traductor_espanol_turco.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.traductor_espanol_turco.model.DatosVocabulario
import com.example.traductor_espanol_turco.model.Palabra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

enum class EstadoAudio { Inactivo, Grabando, Reproduciendo }

data class DetallePalabraUiState(
    val palabra: Palabra? = null,
    val estadoAudio: EstadoAudio = EstadoAudio.Inactivo,
    val hayGrabacion: Boolean = false,
    val permisoDenegado: Boolean = false,
    val mensajeError: String? = null
)

/**
 * Maneja la palabra mostrada y el audio (grabar, detener, reproducir).
 * El id de la palabra llega por la ruta de navegación ("palabraId") a través de SavedStateHandle.
 */
class DetallePalabraViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(DetallePalabraUiState())
    val estado: StateFlow<DetallePalabraUiState> = _estado.asStateFlow()

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var archivoAudio: File? = null

    init {
        val id = savedStateHandle.get<Int>("palabraId")
        val palabra = DatosVocabulario.palabras.find { it.id == id }
        _estado.update {
            it.copy(
                palabra = palabra,
                mensajeError = if (palabra == null) "No se encontró la palabra seleccionada." else null
            )
        }
    }

    /** La pantalla informa el resultado de la solicitud del permiso del micrófono. */
    fun onPermisoResultado(concedido: Boolean) {
        if (concedido) {
            _estado.update { it.copy(permisoDenegado = false) }
            iniciarGrabacion()
        } else {
            _estado.update {
                it.copy(
                    permisoDenegado = true,
                    mensajeError = "Necesitas permitir el micrófono para grabar tu voz."
                )
            }
        }
    }

    fun iniciarGrabacion() {
        if (_estado.value.estadoAudio != EstadoAudio.Inactivo) return
        liberarReproductor()

        val archivo = File(getApplication<Application>().cacheDir, "grabacion_${System.currentTimeMillis()}.m4a")
        try {
            val nuevo = crearRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(archivo.absolutePath)
                prepare()
                start()
            }
            borrarArchivoAnterior()
            recorder = nuevo
            archivoAudio = archivo
            _estado.update { it.copy(estadoAudio = EstadoAudio.Grabando, mensajeError = null) }
        } catch (e: Exception) {
            liberarRecorder()
            archivo.delete()
            _estado.update {
                it.copy(
                    estadoAudio = EstadoAudio.Inactivo,
                    mensajeError = "No se pudo iniciar la grabación. Intenta nuevamente."
                )
            }
        }
    }

    fun detenerGrabacion() {
        if (_estado.value.estadoAudio != EstadoAudio.Grabando) return
        try {
            recorder?.stop()
            _estado.update {
                it.copy(estadoAudio = EstadoAudio.Inactivo, hayGrabacion = true, mensajeError = null)
            }
        } catch (e: RuntimeException) {
            // stop() falla si se detiene casi de inmediato (sin audio capturado)
            archivoAudio?.delete()
            archivoAudio = null
            _estado.update {
                it.copy(
                    estadoAudio = EstadoAudio.Inactivo,
                    hayGrabacion = false,
                    mensajeError = "La grabación es muy corta. Mantén la grabación un poco más."
                )
            }
        } finally {
            liberarRecorder()
        }
    }

    fun reproducir() {
        val archivo = archivoAudio
        if (_estado.value.estadoAudio != EstadoAudio.Inactivo) return
        if (archivo == null || !archivo.exists()) {
            _estado.update { it.copy(hayGrabacion = false, mensajeError = "Primero graba tu voz.") }
            return
        }
        try {
            val nuevo = MediaPlayer().apply {
                setDataSource(archivo.absolutePath)
                setOnCompletionListener { detenerReproduccion() }
                setOnErrorListener { _, _, _ ->
                    liberarReproductor()
                    _estado.update {
                        it.copy(
                            estadoAudio = EstadoAudio.Inactivo,
                            mensajeError = "Ocurrió un error al reproducir el audio."
                        )
                    }
                    true
                }
                prepare()
                start()
            }
            player = nuevo
            _estado.update { it.copy(estadoAudio = EstadoAudio.Reproduciendo, mensajeError = null) }
        } catch (e: Exception) {
            liberarReproductor()
            _estado.update {
                it.copy(
                    estadoAudio = EstadoAudio.Inactivo,
                    mensajeError = "No se pudo reproducir la grabación."
                )
            }
        }
    }

    fun detenerReproduccion() {
        liberarReproductor()
        _estado.update {
            if (it.estadoAudio == EstadoAudio.Reproduciendo) it.copy(estadoAudio = EstadoAudio.Inactivo) else it
        }
    }

    fun limpiarError() {
        _estado.update { it.copy(mensajeError = null) }
    }

    private fun crearRecorder(): MediaRecorder =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(getApplication<Application>())
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }

    private fun borrarArchivoAnterior() {
        archivoAudio?.delete()
        archivoAudio = null
    }

    private fun liberarRecorder() {
        try {
            recorder?.release()
        } catch (_: Exception) {
        }
        recorder = null
    }

    private fun liberarReproductor() {
        try {
            player?.release()
        } catch (_: Exception) {
        }
        player = null
    }

    override fun onCleared() {
        liberarRecorder()
        liberarReproductor()
        archivoAudio?.delete()
        super.onCleared()
    }
}