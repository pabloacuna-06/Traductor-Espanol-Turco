package com.example.traductor_espanol_turco.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.traductor_espanol_turco.view.DetallePalabraScreen
import com.example.traductor_espanol_turco.view.FavoritosScreen
import com.example.traductor_espanol_turco.view.PerfilScreen
import com.example.traductor_espanol_turco.viewmodel.FavoritosViewModel
import com.example.traductor_espanol_turco.viewmodel.PerfilViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val vocabularioViewModel: VocabularioViewModel = viewModel()
    val estado by vocabularioViewModel.estado.collectAsStateWithLifecycle()
    val perfilViewModel: PerfilViewModel = viewModel()
    val estadoPerfil by perfilViewModel.estado.collectAsStateWithLifecycle()
    val favoritosViewModel: FavoritosViewModel = viewModel()
    val estadoFavoritos by favoritosViewModel.estado.collectAsStateWithLifecycle()


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
                onVerFavoritos = { navController.navigate("favoritos") },
                onVerPerfil = { navController.navigate("perfil") }
            )
        }

        composable("palabras") {
            PalabrasScreen(
                nombreCategoria = estado.categoriaSeleccionada?.nombre
                    ?: "Palabras",
                palabras = estado.palabras,
                onAbrirPalabra = { id ->
                    navController.navigate("detalle/$id")
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            "detalle/{palabraId}",
            arguments = listOf(navArgument("palabraId") { type = NavType.IntType })
        ) { backStackEntry ->
            val palabraId = backStackEntry.arguments?.getInt("palabraId") ?: -1
            DetallePalabraScreen(
                esFavorito = palabraId in estadoFavoritos.idsFavoritos,
                onToggleFavorito = { id -> favoritosViewModel.alternarFavorito(id) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                palabras = estadoFavoritos.palabras,
                onEliminar = { palabraId ->
                    favoritosViewModel.eliminarFavorito(palabraId)
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("perfil") {
            PerfilScreen(
                estado = estadoPerfil,
                onCambiarNombre = perfilViewModel::cambiarNombre,
                onCambiarCorreo = perfilViewModel::cambiarCorreo,
                onCambiarEdad = perfilViewModel::cambiarEdad,
                onGuardar = perfilViewModel::guardarPerfil,
                onEditar = perfilViewModel::editarPerfil,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}