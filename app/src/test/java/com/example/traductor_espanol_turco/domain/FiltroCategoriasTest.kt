package com.example.traductor_espanol_turco.domain

import com.example.traductor_espanol_turco.model.DatosVocabulario
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FiltroCategoriasTest {

    @Test
    fun `filtrar devuelve solo palabras de la categoria solicitada`() {
        val resultado = FiltroCategorias.filtrar(
            palabras = DatosVocabulario.palabras,
            categoriaId = 1
        )

        assertEquals(4, resultado.size)
        assertTrue(resultado.all { palabra -> palabra.categoriaId == 1 })
    }

    @Test
    fun `filtrar categoria inexistente devuelve lista vacia`() {
        val resultado = FiltroCategorias.filtrar(
            palabras = DatosVocabulario.palabras,
            categoriaId = 999
        )

        assertTrue(resultado.isEmpty())
    }
}
