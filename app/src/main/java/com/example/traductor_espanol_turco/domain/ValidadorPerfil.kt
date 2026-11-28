package com.example.traductor_espanol_turco.domain

import com.example.traductor_espanol_turco.model.ErrorPerfil

data class ResultadoValidacionPerfil(
    val errorNombre: ErrorPerfil? = null,
    val errorCorreo: ErrorPerfil? = null,
    val errorEdad: ErrorPerfil? = null
) {
    val esValido: Boolean
        get() = errorNombre == null && errorCorreo == null && errorEdad == null
}

object ValidadorPerfil {
    private val formatoCorreo =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validar(
        nombre: String,
        correo: String,
        edad: String
    ): ResultadoValidacionPerfil {
        val nombreLimpio = nombre.trim()
        val correoLimpio = correo.trim()
        val edadLimpia = edad.trim()
        val edadNumero = edadLimpia.toIntOrNull()

        val errorNombre = when {
            nombreLimpio.isEmpty() -> ErrorPerfil.NOMBRE_OBLIGATORIO
            nombreLimpio.length < 2 -> ErrorPerfil.NOMBRE_CORTO
            else -> null
        }

        val errorCorreo = when {
            correoLimpio.isEmpty() -> ErrorPerfil.CORREO_OBLIGATORIO
            !formatoCorreo.matches(correoLimpio) -> ErrorPerfil.CORREO_INVALIDO
            else -> null
        }

        val errorEdad = when {
            edadLimpia.isEmpty() -> ErrorPerfil.EDAD_OBLIGATORIA
            edadNumero == null -> ErrorPerfil.EDAD_NO_NUMERICA
            edadNumero !in 5..120 -> ErrorPerfil.EDAD_FUERA_DE_RANGO
            else -> null
        }

        return ResultadoValidacionPerfil(
            errorNombre = errorNombre,
            errorCorreo = errorCorreo,
            errorEdad = errorEdad
        )
    }
}
