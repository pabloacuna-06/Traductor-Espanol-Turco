package com.example.traductor_espanol_turco.model

data class PerfilUiState(
    val nombre: String = "",
    val correo: String = "",
    val edad: String = "",
    val errorNombre: ErrorPerfil? = null,
    val errorCorreo: ErrorPerfil? = null,
    val errorEdad: ErrorPerfil? = null,
    val guardado: Boolean = false,
    val modoEdicion: Boolean = true
)

enum class ErrorPerfil(val espanol: String, val turco: String) {
    NOMBRE_OBLIGATORIO("El nombre es obligatorio", "Ad zorunludur"),
    NOMBRE_CORTO("El nombre es muy corto", "Ad çok kısa"),
    CORREO_OBLIGATORIO("El correo es obligatorio", "E-posta adresi zorunludur"),
    CORREO_INVALIDO("El correo no es válido", "E-posta adresi geçersiz"),
    EDAD_OBLIGATORIA("La edad es obligatoria", "Yaş zorunludur"),
    EDAD_NO_NUMERICA("La edad debe ser un número", "Yaş bir sayı olmalıdır"),
    EDAD_FUERA_DE_RANGO("La edad debe estar entre 5 y 120", "Yaş 5 ile 120 arasında olmalıdır")
}
