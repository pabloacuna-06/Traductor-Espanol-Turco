package com.example.traductor_espanol_turco.model

data class PerfilUiState(
    val nombre: String = "",
    val correo: String = "",
    val edad: String = "",
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorEdad: String? = null,
    val guardado: Boolean = false,
    val modoEdicion: Boolean = true
)
