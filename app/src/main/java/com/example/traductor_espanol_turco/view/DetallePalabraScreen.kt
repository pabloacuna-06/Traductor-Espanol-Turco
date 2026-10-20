package com.example.traductor_espanol_turco.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.traductor_espanol_turco.viewmodel.DetallePalabraViewModel
import com.example.traductor_espanol_turco.viewmodel.EstadoAudio
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

/**
 * Muestra la traducción, las frases y las opciones para practicar con audio.
 *
 * @param esFavorito indica si la palabra está guardada como favorita.
 * @param onToggleFavorito permite agregar o quitar la palabra de favoritos.
 */
@Composable
fun DetallePalabraScreen(
    esFavorito: Boolean,
    onToggleFavorito: (Int) -> Unit,
    onVolver: () -> Unit,
    viewModel: DetallePalabraViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val palabra = estado.palabra

    // La tarjeta aparece suavemente al abrir una palabra.
    val opacidadTarjeta = remember(palabra?.id) { Animatable(0f) }

    LaunchedEffect(palabra?.id) {
        if (palabra != null) {
            opacidadTarjeta.animateTo(
                targetValue = 1f,
                animationSpec = tween(2000)
            )
        }
    }

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido -> viewModel.onPermisoResultado(concedido) }

    val grabando = estado.estadoAudio == EstadoAudio.Grabando
    val reproduciendo = estado.estadoAudio == EstadoAudio.Reproduciendo

    // Animación: el indicador de grabación pulsa mientras se graba.
    val pulso = rememberInfiniteTransition(label = "pulso")
    val alphaPulso by pulso.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse),
        label = "alphaPulso"
    )
    val colorGrabar by animateColorAsState(
        targetValue = if (grabando) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        animationSpec = tween(300),
        label = "colorGrabar"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(onClick = onVolver) {
            Text("Volver a palabras")
        }

        if (palabra == null) {
            MensajeError(estado.mensajeError ?: "No se encontró la palabra.")
            return@Column
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(opacidadTarjeta.value)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Español", style = MaterialTheme.typography.labelMedium)
                Text(palabra.espanol, style = MaterialTheme.typography.headlineMedium)
                Text(palabra.fraseEspanol, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text("Türkçe", style = MaterialTheme.typography.labelMedium)
                Text(palabra.turco, style = MaterialTheme.typography.headlineMedium)
                Text(palabra.fraseTurco, style = MaterialTheme.typography.bodyLarge)
            }
        }

        // Agrega o quita la palabra de favoritos.
        OutlinedButton(
            onClick = { onToggleFavorito(palabra.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null
            )
            Spacer(Modifier.padding(horizontal = 4.dp))
            Text(if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos")
        }

        Text("Practica tu pronunciación", style = MaterialTheme.typography.titleMedium)

        if (grabando) {
            Text(
                "● Grabando...",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.alpha(alphaPulso)
            )
        }
        if (reproduciendo) {
            Text("Reproduciendo tu grabación...")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!grabando) {
                Button(
                    onClick = {
                        val tienePermiso = ContextCompat.checkSelfPermission(
                            context, Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                        if (tienePermiso) {
                            viewModel.iniciarGrabacion()
                        } else {
                            permisoLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    },
                    enabled = !reproduciendo,
                    colors = ButtonDefaults.buttonColors(containerColor = colorGrabar),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Grabar")
                }
            } else {
                Button(
                    onClick = { viewModel.detenerGrabacion() },
                    colors = ButtonDefaults.buttonColors(containerColor = colorGrabar),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Detener")
                }
            }

            if (!reproduciendo) {
                OutlinedButton(
                    onClick = { viewModel.reproducir() },
                    enabled = estado.hayGrabacion && !grabando,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    Text("Reproducir")
                }
            } else {
                OutlinedButton(
                    onClick = { viewModel.detenerReproduccion() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Detener")
                }
            }
        }

        // Mensajes de error con ícono (permiso rechazado, errores de audio).
        AnimatedVisibility(
            visible = estado.mensajeError != null,
            enter = fadeIn(tween(300)) + expandVertically(tween(300)),
            exit = fadeOut(tween(200)) + shrinkVertically(tween(200))
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MensajeError(estado.mensajeError ?: "")
                if (estado.permisoDenegado) {
                    OutlinedButton(onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }) {
                        Text("Abrir ajustes de la app")
                    }
                }
            }
        }
    }
}

@Composable
private fun MensajeError(mensaje: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
        Text(mensaje, color = MaterialTheme.colorScheme.error)
    }
}
