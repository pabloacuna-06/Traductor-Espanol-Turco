package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.traductor_espanol_turco.model.ConocePaisesUiState
import com.example.traductor_espanol_turco.model.PaisInfo

@Composable
fun ConocePaisesScreen(
    estado: ConocePaisesUiState,
    onReintentar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Conoce Chile y Turquía",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Descubre sus banderas y capitales con datos obtenidos desde una API pública.",
            style = MaterialTheme.typography.bodyLarge
        )

        when {
            estado.cargando -> Cargando()
            estado.error != null -> ErrorCarga(
                mensaje = estado.error,
                onReintentar = onReintentar
            )
            else -> estado.paises.forEach { pais ->
                TarjetaPais(pais)
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a categorías")
        }

        Text(
            text = "Fuente: countries.dev",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun Cargando() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator()
            Text("Cargando países…")
        }
    }
}

@Composable
private fun ErrorCarga(
    mensaje: String,
    onReintentar: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "No se pudieron obtener los países",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )
            Text(mensaje)
            Button(
                onClick = onReintentar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
private fun TarjetaPais(pais: PaisInfo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = pais.banderaUrl,
                contentDescription = "Bandera de ${pais.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
            Text(
                text = pais.nombre,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Capital: ${pais.capital}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
