package com.example.traductor_espanol_turco.domain

import com.example.traductor_espanol_turco.model.ErrorPerfil

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
        assertEquals(ErrorPerfil.NOMBRE_OBLIGATORIO, resultado.errorNombre)
        assertEquals(ErrorPerfil.CORREO_OBLIGATORIO, resultado.errorCorreo)
        assertEquals(ErrorPerfil.EDAD_OBLIGATORIA, resultado.errorEdad)
    }

    @Test
    fun `correo invalido es rechazado`() {
        val resultado = ValidadorPerfil.validar("Ana", "correo-invalido", "20")

        assertEquals(ErrorPerfil.CORREO_INVALIDO, resultado.errorCorreo)
    }

    @Test
    fun `edad no numerica es rechazada`() {
        val resultado = ValidadorPerfil.validar("Ana", "ana@example.com", "veinte")

        assertEquals(ErrorPerfil.EDAD_NO_NUMERICA, resultado.errorEdad)
    }

    @Test
    fun `edad fuera de rango es rechazada`() {
        val resultado = ValidadorPerfil.validar("Ana", "ana@example.com", "121")

        assertEquals(ErrorPerfil.EDAD_FUERA_DE_RANGO, resultado.errorEdad)
    }
}
