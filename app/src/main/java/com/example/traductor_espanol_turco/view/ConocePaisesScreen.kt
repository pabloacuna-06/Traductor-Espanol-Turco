package com.example.traductor_espanol_turco.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.traductor_espanol_turco.model.ConocePaisesUiState
import com.example.traductor_espanol_turco.model.Idioma
import com.example.traductor_espanol_turco.model.PaisInfo

@Composable
fun ConocePaisesScreen(
    estado: ConocePaisesUiState,
    idioma: Idioma,
    onReintentar: () -> Unit,
    onVolver: () -> Unit
) {
    val esEspanol = idioma == Idioma.ESPANOL

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                Text(
                    text = if (esEspanol) "Volver a categorías" else "Kategorilere dön",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (esEspanol) "Conoce Chile y Turquía" else "Şili ve Türkiye'yi tanı",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = if (esEspanol) "Banderas y capitales" else "Bayraklar ve başkentler",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            when {
                estado.cargando -> Cargando(esEspanol)
                estado.hayError -> ErrorCarga(esEspanol, onReintentar)
                else -> estado.paises.forEach { pais ->
                    TarjetaPais(pais, esEspanol)
                }
            }

            Text(
                text = if (esEspanol) "Fuente: countries.dev" else "Kaynak: countries.dev",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun Cargando(esEspanol: Boolean) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 128.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            CircularProgressIndicator(modifier = Modifier.size(28.dp))
            Text(if (esEspanol) "Cargando países…" else "Ülke bilgileri yükleniyor…")
        }
    }
}

@Composable
private fun ErrorCarga(
    esEspanol: Boolean,
    onReintentar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (esEspanol) {
                    "No se pudieron obtener los países"
                } else {
                    "Ülke bilgileri yüklenemedi"
                },
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                if (esEspanol) {
                    "Revisa tu conexión e inténtalo nuevamente."
                } else {
                    "İnternet bağlantını kontrol edip tekrar dene."
                }
            )
            Button(
                onClick = onReintentar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (esEspanol) "Reintentar" else "Tekrar dene")
            }
        }
    }
}

@Composable
private fun TarjetaPais(pais: PaisInfo, esEspanol: Boolean) {
    val nombre = if (esEspanol) {
        pais.nombre
    } else {
        when (pais.codigo) {
            "CL" -> "Şili"
            "TR" -> "Türkiye"
            else -> pais.nombre
        }
    }
    val etiquetaCapital = if (esEspanol) "Capital" else "Başkent"
    val formaBandera = RoundedCornerShape(8.dp)

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pais.banderaUrl,
                contentDescription = if (esEspanol) "Bandera de $nombre" else "$nombre bayrağı",
                modifier = Modifier
                    .size(width = 108.dp, height = 72.dp)
                    .clip(formaBandera)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, formaBandera),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = nombre, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "$etiquetaCapital: ${pais.capital}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
