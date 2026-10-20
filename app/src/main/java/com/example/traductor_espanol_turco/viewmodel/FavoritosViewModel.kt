package com.example.traductor_espanol_turco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.traductor_espanol_turco.data.AlmacenLocal
import com.example.traductor_espanol_turco.model.DatosVocabulario
import com.example.traductor_espanol_turco.model.FavoritosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val almacen = AlmacenLocal(application)

    private val _estado = MutableStateFlow(FavoritosUiState())
    val estado: StateFlow<FavoritosUiState> = _estado.asStateFlow()

    init {
        // Al abrir la app se recuperan los favoritos guardados en DataStore
        // y el estado se actualiza solo cada vez que cambian
        viewModelScope.launch {
            almacen.favoritos.collect { ids -> actualizarEstado(ids) }
        }
    }

    // Función para el botón de favoritos de la pantalla de detalle:
    // si la palabra ya es favorita la quita, y si no, la agrega.
    fun alternarFavorito(palabraId: Int) {
        val ids = _estado.value.idsFavoritos
        val nuevos = if (palabraId in ids) ids - palabraId else ids + palabraId
        guardar(nuevos)
    }

    // Sirve para saber si el corazón del detalle debe verse marcado.
    fun esFavorito(palabraId: Int): Boolean {
        return palabraId in _estado.value.idsFavoritos
    }

    fun eliminarFavorito(palabraId: Int) {
        val nuevos = _estado.value.idsFavoritos - palabraId
        guardar(nuevos)
    }

    private fun guardar(ids: Set<Int>) {
        // Se actualiza la pantalla al tiro y luego se guarda en DataStore
        actualizarEstado(ids)
        viewModelScope.launch {
            almacen.guardarFavoritos(ids)
        }
    }

    private fun actualizarEstado(ids: Set<Int>) {
        val palabras = DatosVocabulario.palabras.filter { it.id in ids }
        _estado.value = FavoritosUiState(
            idsFavoritos = ids,
            palabras = palabras
        )
    }
}
