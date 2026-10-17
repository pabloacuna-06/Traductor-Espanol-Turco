package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.traductor_espanol_turco.model.Categoria

@Composable
fun CategoriasScreen(
    categorias: List<Categoria>,
    onSeleccionarCategoria: (Int) -> Unit,
    onVerFavoritos: () -> Unit,
    onVerPerfil: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Elige una categoría")

        categorias.forEach { categoria ->
            Button(
                onClick = {
                    onSeleccionarCategoria(categoria.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(categoria.nombre)
            }
        }

        Button(onClick = onVerFavoritos) {
            Text("Mis favoritos")
        }

        Button(onClick = onVerPerfil) {
            Text("Mi perfil")
        }
    }
}