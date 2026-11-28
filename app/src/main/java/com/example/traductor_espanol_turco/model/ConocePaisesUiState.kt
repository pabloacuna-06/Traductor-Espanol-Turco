package com.example.traductor_espanol_turco.model

data class ConocePaisesUiState(
    val cargando: Boolean = false,
    val paises: List<PaisInfo> = emptyList(),
    val error: String? = null
)
