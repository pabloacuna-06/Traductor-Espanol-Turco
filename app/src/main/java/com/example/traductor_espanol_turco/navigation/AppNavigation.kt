package com.example.traductor_espanol_turco.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.traductor_espanol_turco.view.CategoriasScreen
import com.example.traductor_espanol_turco.view.PalabrasScreen
import com.example.traductor_espanol_turco.viewmodel.VocabularioViewModel
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val vocabularioViewModel: VocabularioViewModel = viewModel()
    val estado by vocabularioViewModel.estado.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val mostrarPendiente: () -> Unit = {
        Toast.makeText(
            context,
            "Esta sección todavía está en desarrollo.",
            Toast.LENGTH_SHORT
        ).show()
    }

    NavHost(
        navController = navController,
        startDestination = "categorias",
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        composable("categorias") {
            CategoriasScreen(
                categorias = estado.categorias,
                onSeleccionarCategoria = { categoriaId ->
                    vocabularioViewModel.seleccionarCategoria(categoriaId)
                    navController.navigate("palabras")
                },
                onVerFavoritos = mostrarPendiente,
                onVerPerfil = mostrarPendiente
            )
        }

        composable("palabras") {
            PalabrasScreen(
                nombreCategoria = estado.categoriaSeleccionada?.nombre
                    ?: "Palabras",
                palabras = estado.palabras,
                onAbrirPalabra = { _ ->
                    mostrarPendiente()
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}