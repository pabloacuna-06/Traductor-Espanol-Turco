package com.example.traductor_espanol_turco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.traductor_espanol_turco.data.AlmacenLocal
import com.example.traductor_espanol_turco.model.Perfil
import com.example.traductor_espanol_turco.model.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val almacen = AlmacenLocal(application)

    private val _estado = MutableStateFlow(PerfilUiState())
    val estado: StateFlow<PerfilUiState> = _estado.asStateFlow()

    init {
        // Al abrir la app se recupera el perfil guardado en DataStore
        viewModelScope.launch {
            val perfil = almacen.perfil.first()
            _estado.value = PerfilUiState(
                nombre = perfil.nombre,
                correo = perfil.correo,
                edad = perfil.edad,
                // Si ya hay un perfil guardado, se muestra el resumen y no el formulario
                modoEdicion = perfil.nombre.isEmpty()
            )
        }
    }

    fun editarPerfil() {
        _estado.update { it.copy(modoEdicion = true, guardado = false) }
    }

    fun cambiarNombre(nuevo: String) {
        _estado.update { it.copy(nombre = nuevo, errorNombre = null, guardado = false) }
    }

    fun cambiarCorreo(nuevo: String) {
        _estado.update { it.copy(correo = nuevo, errorCorreo = null, guardado = false) }
    }

    fun cambiarEdad(nuevo: String) {
        _estado.update { it.copy(edad = nuevo, errorEdad = null, guardado = false) }
    }

    fun guardarPerfil() {
        val actual = _estado.value
        val nombre = actual.nombre.trim()
        val correo = actual.correo.trim()
        val edad = actual.edad.trim()

        val errorNombre = when {
            nombre.isEmpty() -> "El nombre es obligatorio"
            nombre.length < 2 -> "El nombre es muy corto"
            else -> null
        }

        val errorCorreo = when {
            correo.isEmpty() -> "El correo es obligatorio"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                "El correo no es válido"
            else -> null
        }

        val edadNumero = edad.toIntOrNull()
        val errorEdad = when {
            edad.isEmpty() -> "La edad es obligatoria"
            edadNumero == null -> "La edad debe ser un número"
            edadNumero < 5 || edadNumero > 120 -> "La edad debe estar entre 5 y 120"
            else -> null
        }

        if (errorNombre != null || errorCorreo != null || errorEdad != null) {
            _estado.update {
                it.copy(
                    errorNombre = errorNombre,
                    errorCorreo = errorCorreo,
                    errorEdad = errorEdad,
                    guardado = false
                )
            }
            return
        }

        viewModelScope.launch {
            almacen.guardarPerfil(Perfil(nombre, correo, edad))
        }
        _estado.update {
            it.copy(
                nombre = nombre,
                correo = correo,
                edad = edad,
                guardado = true,
                modoEdicion = false
            )
        }
    }
}
