package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.traductor_espanol_turco.model.Categoria
import com.example.traductor_espanol_turco.model.Idioma

@Composable
fun CategoriasScreen(
    categorias: List<Categoria>,
    idioma: Idioma,
    onSeleccionarCategoria: (Int) -> Unit,
    onVerFavoritos: () -> Unit,
    onVerPerfil: () -> Unit,
    onConocerPaises: () -> Unit
) {
    val esEspanol = idioma == Idioma.ESPANOL

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "¡Hola Merhaba!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = if (esEspanol) {
                "Elige una categoría"
            } else {
                "Bir kategori seç"
            },
            style = MaterialTheme.typography.titleLarge
        )

        categorias.forEach { categoria ->
            Card(
                onClick = {
                    onSeleccionarCategoria(categoria.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (esEspanol) {
                        categoria.nombre
                    } else {
                        categoria.nombreTurco
                    },
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        OutlinedButton(
            onClick = onConocerPaises,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (esEspanol) {
                    "Conoce Chile y Turquía"
                } else {
                    "Şili ve Türkiye'yi tanı"
                }
            )
        }

        OutlinedButton(
            onClick = onVerFavoritos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (esEspanol) {
                    "Mis favoritos"
                } else {
                    "Favorilerim"
                }
            )
        }

        OutlinedButton(
            onClick = onVerPerfil,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (esEspanol) {
                    "Mi perfil"
                } else {
                    "Profilim"
                }
            )
        }
    }
}
