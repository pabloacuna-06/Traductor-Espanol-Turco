package com.example.traductor_espanol_turco.model

data class VocabularioUiState(
    val categorias: List<Categoria> = emptyList(),
    val categoriaSeleccionada: Categoria? = null,
    val palabras: List<Palabra> = emptyList()
)