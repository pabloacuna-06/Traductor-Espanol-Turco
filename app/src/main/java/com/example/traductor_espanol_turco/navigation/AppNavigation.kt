package com.example.traductor_espanol_turco.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.traductor_espanol_turco.view.CategoriasScreen
import com.example.traductor_espanol_turco.view.ConocePaisesScreen
import com.example.traductor_espanol_turco.view.DetallePalabraScreen
import com.example.traductor_espanol_turco.view.FavoritosScreen
import com.example.traductor_espanol_turco.view.IdiomaScreen
import com.example.traductor_espanol_turco.view.PalabrasScreen
import com.example.traductor_espanol_turco.view.PerfilScreen
import com.example.traductor_espanol_turco.viewmodel.FavoritosViewModel
import com.example.traductor_espanol_turco.viewmodel.ConocePaisesViewModel
import com.example.traductor_espanol_turco.viewmodel.PerfilViewModel
import com.example.traductor_espanol_turco.viewmodel.VocabularioViewModel

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
        startDestination = "idioma",
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
        composable("idioma") {
            IdiomaScreen(
                onSeleccionarIdioma = { idioma ->
                    vocabularioViewModel.seleccionarIdioma(idioma)
                    navController.navigate("categorias") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("categorias") {
            CategoriasScreen(
                categorias = estado.categorias,
                idioma = estado.idioma,
                onSeleccionarCategoria = { categoriaId ->
                    vocabularioViewModel.seleccionarCategoria(categoriaId)
                    navController.navigate("palabras")
                },
                onVerFavoritos = {
                    navController.navigate("favoritos")
                },
                onVerPerfil = {
                    navController.navigate("perfil")
                },
                onConocerPaises = {
                    navController.navigate("conoce_paises")
                }
            )
        }

        composable("conoce_paises") {
            val conocePaisesViewModel: ConocePaisesViewModel = viewModel()
            val estadoPaises by conocePaisesViewModel.estado.collectAsStateWithLifecycle()

            ConocePaisesScreen(
                estado = estadoPaises,
                idioma = estado.idioma,
                onReintentar = conocePaisesViewModel::reintentar,
                onVolver = { navController.popBackStack() }
            )
        }

        composable("palabras") {
            val esEspanol =
                estado.idioma == com.example.traductor_espanol_turco.model.Idioma.ESPANOL

            val nombreCategoria = if (esEspanol) {
                estado.categoriaSeleccionada?.nombre ?: "Palabras"
            } else {
                estado.categoriaSeleccionada?.nombreTurco ?: "Kelimeler"
            }

            PalabrasScreen(
                nombreCategoria = nombreCategoria,
                palabras = estado.palabras,
                idioma = estado.idioma,
                onAbrirPalabra = { id ->
                    navController.navigate("detalle/$id")
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "detalle/{palabraId}",
            arguments = listOf(
                navArgument("palabraId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val palabraId = backStackEntry.arguments
                ?.getInt("palabraId") ?: -1

            DetallePalabraScreen(
                esFavorito = palabraId in estadoFavoritos.idsFavoritos,
                onToggleFavorito = { id ->
                    favoritosViewModel.alternarFavorito(id)
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                palabras = estadoFavoritos.palabras,
                idioma = estado.idioma,
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
                idioma = estado.idioma,
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
