package com.example.traductor_espanol_turco.model

data class Palabra(
    val id: Int,
    val categoriaId: Int,
    val espanol: String,
    val turco: String,
    val fraseEspanol: String,
    val fraseTurco: String
)