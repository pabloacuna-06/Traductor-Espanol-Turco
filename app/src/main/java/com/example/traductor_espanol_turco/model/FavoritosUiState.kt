package com.example.traductor_espanol_turco.model

data class FavoritosUiState(
    val idsFavoritos: Set<Int> = emptySet(),
    val palabras: List<Palabra> = emptyList()
)
