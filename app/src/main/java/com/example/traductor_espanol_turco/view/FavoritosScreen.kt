package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.traductor_espanol_turco.R
import com.example.traductor_espanol_turco.model.Idioma
import com.example.traductor_espanol_turco.model.Palabra

@Composable
fun FavoritosScreen(
    palabras: List<Palabra>,
    idioma: Idioma,
    onEliminar: (Int) -> Unit,
    onVolver: () -> Unit
) {
    val esEspanol = idioma == Idioma.ESPANOL

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (esEspanol) "Mis favoritos" else "Favorilerim",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = onVolver) {
            Text(if (esEspanol) "Volver a categorías" else "Kategorilere dön")
        }

        if (palabras.isEmpty()) {
            Text(
                if (esEspanol) "Todavía no tienes palabras favoritas."
                else "Henüz favori kelimen yok."
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = palabras,
                key = { palabra -> palabra.id }
            ) { palabra ->
                // Animación: al eliminar, los demás elementos se acomodan suavemente
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (esEspanol) palabra.espanol else palabra.turco,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(if (esEspanol) palabra.turco else palabra.espanol)
                        }
                        IconButton(onClick = { onEliminar(palabra.id) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = if (esEspanol) {
                                    "Eliminar de favoritos"
                                } else {
                                    "Favorilerden kaldır"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
