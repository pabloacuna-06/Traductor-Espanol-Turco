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
import com.example.traductor_espanol_turco.model.Palabra

@Composable
fun FavoritosScreen(
    palabras: List<Palabra>,
    onEliminar: (Int) -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Mis favoritos", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = onVolver) {
            Text("Volver a categorías")
        }

        if (palabras.isEmpty()) {
            Text("Todavía no tienes palabras favoritas.")
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
                            Text(palabra.espanol, style = MaterialTheme.typography.titleMedium)
                            Text(palabra.turco)
                        }
                        IconButton(onClick = { onEliminar(palabra.id) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "Eliminar de favoritos"
                            )
                        }
                    }
                }
            }
        }
    }
}
