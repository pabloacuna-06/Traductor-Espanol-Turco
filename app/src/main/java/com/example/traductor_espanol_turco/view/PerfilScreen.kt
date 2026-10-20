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
import com.example.traductor_espanol_turco.model.PerfilUiState

@Composable
fun PerfilScreen(
    estado: PerfilUiState,
    onCambiarNombre: (String) -> Unit,
    onCambiarCorreo: (String) -> Unit,
    onCambiarEdad: (String) -> Unit,
    onGuardar: () -> Unit,
    onEditar: () -> Unit,
    onVolver: () -> Unit
) {
    // Animación: cambia suavemente entre el formulario y el perfil guardado
    Crossfade(targetState = estado.modoEdicion, label = "perfil") { editando ->
        if (editando) {
            FormularioPerfil(
                estado = estado,
                onCambiarNombre = onCambiarNombre,
                onCambiarCorreo = onCambiarCorreo,
                onCambiarEdad = onCambiarEdad,
                onGuardar = onGuardar,
                onVolver = onVolver
            )
        } else {
            ResumenPerfil(
                estado = estado,
                onEditar = onEditar,
                onVolver = onVolver
            )
        }
    }
}

@Composable
private fun FormularioPerfil(
    estado: PerfilUiState,
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
        Text("Mi perfil", style = MaterialTheme.typography.headlineMedium)

        CampoPerfil(
            valor = estado.nombre,
            etiqueta = "Nombre",
            error = estado.errorNombre,
            tipoTeclado = KeyboardType.Text,
            onCambio = onCambiarNombre
        )

        CampoPerfil(
            valor = estado.correo,
            etiqueta = "Correo",
            error = estado.errorCorreo,
            tipoTeclado = KeyboardType.Email,
            onCambio = onCambiarCorreo
        )

        CampoPerfil(
            valor = estado.edad,
            etiqueta = "Edad",
            error = estado.errorEdad,
            tipoTeclado = KeyboardType.Number,
            onCambio = onCambiarEdad
        )

        // Animación: el mensaje de guardado aparece y desaparece suavemente
        AnimatedVisibility(visible = estado.guardado) {
            Text(
                text = "Perfil guardado correctamente",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = onGuardar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar perfil")
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a categorías")
        }
    }
}

@Composable
private fun ResumenPerfil(
    estado: PerfilUiState,
    onEditar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Mi perfil", style = MaterialTheme.typography.headlineMedium)

        Text(
            text = "¡Hola, ${estado.nombre}!",
            style = MaterialTheme.typography.titleLarge
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nombre: ${estado.nombre}")
                Text("Correo: ${estado.correo}")
                Text("Edad: ${estado.edad} años")
            }
        }

        Button(
            onClick = onEditar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar perfil")
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a categorías")
        }
    }
}

@Composable
private fun CampoPerfil(
    valor: String,
    etiqueta: String,
    error: String?,
    tipoTeclado: KeyboardType,
    onCambio: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = valor,
            onValueChange = onCambio,
            label = { Text(etiqueta) },
            singleLine = true,
            isError = error != null,
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
            // Ícono de error dentro del campo
            trailingIcon = {
                if (error != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Animación: el mensaje de error aparece suavemente
        AnimatedVisibility(visible = error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
