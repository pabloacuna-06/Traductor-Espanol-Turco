package com.example.traductor_espanol_turco.domain

import com.example.traductor_espanol_turco.model.Palabra

object FiltroCategorias {
    fun filtrar(
        palabras: List<Palabra>,
        categoriaId: Int
    ): List<Palabra> = palabras.filter { palabra ->
        palabra.categoriaId == categoriaId
    }
}
