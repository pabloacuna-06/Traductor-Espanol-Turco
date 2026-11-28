package com.example.traductor_espanol_turco.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.traductor_espanol_turco.R
import com.example.traductor_espanol_turco.model.ErrorPerfil
import com.example.traductor_espanol_turco.model.Idioma
import com.example.traductor_espanol_turco.model.PerfilUiState

@Composable
fun PerfilScreen(
    estado: PerfilUiState,
    idioma: Idioma,
    onCambiarNombre: (String) -> Unit,
    onCambiarCorreo: (String) -> Unit,
    onCambiarEdad: (String) -> Unit,
    onGuardar: () -> Unit,
    onEditar: () -> Unit,
    onVolver: () -> Unit
) {
    val esEspanol = idioma == Idioma.ESPANOL

    // Animación: cambia suavemente entre el formulario y el perfil guardado
    Crossfade(targetState = estado.modoEdicion, label = "perfil") { editando ->
        if (editando) {
            FormularioPerfil(
                estado = estado,
                esEspanol = esEspanol,
                onCambiarNombre = onCambiarNombre,
                onCambiarCorreo = onCambiarCorreo,
                onCambiarEdad = onCambiarEdad,
                onGuardar = onGuardar,
                onVolver = onVolver
            )
        } else {
            ResumenPerfil(
                estado = estado,
                esEspanol = esEspanol,
                onEditar = onEditar,
                onVolver = onVolver
            )
        }
    }
}

@Composable
private fun FormularioPerfil(
    estado: PerfilUiState,
    esEspanol: Boolean,
    onCambiarNombre: (String) -> Unit,
    onCambiarCorreo: (String) -> Unit,
    onCambiarEdad: (String) -> Unit,
    onGuardar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (esEspanol) "Mi perfil" else "Profilim",
            style = MaterialTheme.typography.headlineMedium
        )

        CampoPerfil(
            valor = estado.nombre,
            etiqueta = if (esEspanol) "Nombre" else "Ad",
            error = estado.errorNombre,
            esEspanol = esEspanol,
            tipoTeclado = KeyboardType.Text,
            onCambio = onCambiarNombre
        )

        CampoPerfil(
            valor = estado.correo,
            etiqueta = if (esEspanol) "Correo" else "E-posta",
            error = estado.errorCorreo,
            esEspanol = esEspanol,
            tipoTeclado = KeyboardType.Email,
            onCambio = onCambiarCorreo
        )

        CampoPerfil(
            valor = estado.edad,
            etiqueta = if (esEspanol) "Edad" else "Yaş",
            error = estado.errorEdad,
            esEspanol = esEspanol,
            tipoTeclado = KeyboardType.Number,
            onCambio = onCambiarEdad
        )

        // Animación: el mensaje de guardado aparece y desaparece suavemente
        AnimatedVisibility(visible = estado.guardado) {
            Text(
                text = if (esEspanol) "Perfil guardado correctamente" else "Profil kaydedildi",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = onGuardar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esEspanol) "Guardar perfil" else "Profili kaydet")
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esEspanol) "Volver a categorías" else "Kategorilere dön")
        }
    }
}

@Composable
private fun ResumenPerfil(
    estado: PerfilUiState,
    esEspanol: Boolean,
    onEditar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (esEspanol) "Mi perfil" else "Profilim",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = if (esEspanol) "¡Hola, ${estado.nombre}!" else "Merhaba, ${estado.nombre}!",
            style = MaterialTheme.typography.titleLarge
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(if (esEspanol) "Nombre: ${estado.nombre}" else "Ad: ${estado.nombre}")
                Text(if (esEspanol) "Correo: ${estado.correo}" else "E-posta: ${estado.correo}")
                Text(if (esEspanol) "Edad: ${estado.edad} años" else "Yaş: ${estado.edad}")
            }
        }

        Button(
            onClick = onEditar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esEspanol) "Editar perfil" else "Profili düzenle")
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esEspanol) "Volver a categorías" else "Kategorilere dön")
        }
    }
}

@Composable
private fun CampoPerfil(
    valor: String,
    etiqueta: String,
    error: ErrorPerfil?,
    esEspanol: Boolean,
    tipoTeclado: KeyboardType,
    onCambio: (String) -> Unit
) {
    val mensajeError = if (esEspanol) error?.espanol else error?.turco

    Column {
        OutlinedTextField(
            value = valor,
            onValueChange = onCambio,
            label = { Text(etiqueta) },
            singleLine = true,
            isError = mensajeError != null,
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
            // Ícono de error dentro del campo
            trailingIcon = {
                if (mensajeError != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = if (esEspanol) "Error" else "Hata",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Animación: el mensaje de error aparece suavemente
        AnimatedVisibility(visible = mensajeError != null) {
            Text(
                text = mensajeError.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
