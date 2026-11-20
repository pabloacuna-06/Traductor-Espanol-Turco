package com.example.traductor_espanol_turco.model

data class Categoria(
    val id: Int,
    val nombre: String,
    val nombreTurco: String = nombre
)