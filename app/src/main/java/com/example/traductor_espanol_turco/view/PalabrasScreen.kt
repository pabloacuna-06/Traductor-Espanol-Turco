package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.traductor_espanol_turco.model.Idioma
import com.example.traductor_espanol_turco.model.Palabra

@Composable
fun PalabrasScreen(
    nombreCategoria: String,
    palabras: List<Palabra>,
    idioma: Idioma,
    onAbrirPalabra: (Int) -> Unit,
    onVolver: () -> Unit
) {
    val esEspanol = idioma == Idioma.ESPANOL

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onVolver) {
            Text(
                text = if (esEspanol) {
                    "Volver a categorías"
                } else {
                    "Kategorilere dön"
                }
            )
        }

        Text(
            text = nombreCategoria,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = if (esEspanol) {
                "Toca una palabra para aprender"
            } else {
                "Öğrenmek için bir kelimeye dokun"
            },
            style = MaterialTheme.typography.bodyLarge
        )

        if (palabras.isEmpty()) {
            Text(
                text = if (esEspanol) {
                    "Todavía no hay palabras en esta categoría."
                } else {
                    "Bu kategoride henüz kelime yok."
                }
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = palabras,
                key = { palabra -> palabra.id }
            ) { palabra ->
                Card(
                    onClick = {
                        onAbrirPalabra(palabra.id)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (esEspanol) {
                            palabra.espanol
                        } else {
                            palabra.turco
                        },
                        modifier = Modifier.padding(24.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}