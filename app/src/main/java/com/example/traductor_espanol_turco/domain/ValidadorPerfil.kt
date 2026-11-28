package com.example.traductor_espanol_turco.domain

data class ResultadoValidacionPerfil(
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorEdad: String? = null
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
            nombreLimpio.isEmpty() -> "El nombre es obligatorio"
            nombreLimpio.length < 2 -> "El nombre es muy corto"
            else -> null
        }

        val errorCorreo = when {
            correoLimpio.isEmpty() -> "El correo es obligatorio"
            !formatoCorreo.matches(correoLimpio) -> "El correo no es válido"
            else -> null
        }

        val errorEdad = when {
            edadLimpia.isEmpty() -> "La edad es obligatoria"
            edadNumero == null -> "La edad debe ser un número"
            edadNumero !in 5..120 -> "La edad debe estar entre 5 y 120"
            else -> null
        }

        return ResultadoValidacionPerfil(
            errorNombre = errorNombre,
            errorCorreo = errorCorreo,
            errorEdad = errorEdad
        )
    }
}
