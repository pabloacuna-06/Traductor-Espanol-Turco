package com.example.traductor_espanol_turco.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidadorPerfilTest {

    @Test
    fun `perfil correcto es valido`() {
        val resultado = ValidadorPerfil.validar(
            nombre = "Cristóbal",
            correo = "cristobal@example.com",
            edad = "20"
        )

        assertTrue(resultado.esValido)
        assertNull(resultado.errorNombre)
        assertNull(resultado.errorCorreo)
        assertNull(resultado.errorEdad)
    }

    @Test
    fun `campos vacios muestran errores obligatorios`() {
        val resultado = ValidadorPerfil.validar(" ", " ", " ")

        assertFalse(resultado.esValido)
        assertEquals("El nombre es obligatorio", resultado.errorNombre)
        assertEquals("El correo es obligatorio", resultado.errorCorreo)
        assertEquals("La edad es obligatoria", resultado.errorEdad)
    }

    @Test
    fun `correo invalido es rechazado`() {
        val resultado = ValidadorPerfil.validar("Ana", "correo-invalido", "20")

        assertEquals("El correo no es válido", resultado.errorCorreo)
    }

    @Test
    fun `edad no numerica es rechazada`() {
        val resultado = ValidadorPerfil.validar("Ana", "ana@example.com", "veinte")

        assertEquals("La edad debe ser un número", resultado.errorEdad)
    }

    @Test
    fun `edad fuera de rango es rechazada`() {
        val resultado = ValidadorPerfil.validar("Ana", "ana@example.com", "121")

        assertEquals("La edad debe estar entre 5 y 120", resultado.errorEdad)
    }
}
