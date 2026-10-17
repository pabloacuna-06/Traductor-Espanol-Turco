package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.traductor_espanol_turco.model.Palabra

@Composable
fun PalabrasScreen(
    nombreCategoria: String,
    palabras: List<Palabra>,
    onAbrirPalabra: (Int) -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(nombreCategoria)

        Button(onClick = onVolver) {
            Text("Volver a categorías")
        }

        if (palabras.isEmpty()) {
            Text("Todavía no hay palabras en esta categoría.")
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = palabras,
                key = { palabra -> palabra.id }
            ) { palabra ->
                Button(
                    onClick = {
                        onAbrirPalabra(palabra.id)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(palabra.espanol)
                }
            }
        }
    }
}