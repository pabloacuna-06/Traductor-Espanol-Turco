package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoriasScreen(
    onVerFavoritos: () -> Unit,
    onVerPerfil: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Categorías")

        Button(onClick = onVerFavoritos) {
            Text("Mis favoritos")
        }

        Button(onClick = onVerPerfil) {
            Text("Mi perfil")
        }
    }
}