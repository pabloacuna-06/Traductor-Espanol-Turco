package com.example.traductor_espanol_turco.viewmodel

import androidx.lifecycle.ViewModel
import com.example.traductor_espanol_turco.domain.FiltroCategorias
import com.example.traductor_espanol_turco.model.DatosVocabulario
import com.example.traductor_espanol_turco.model.Idioma
import com.example.traductor_espanol_turco.model.VocabularioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VocabularioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(
        VocabularioUiState(
            categorias = DatosVocabulario.categorias
        )
    )

    val estado: StateFlow<VocabularioUiState> = _estado.asStateFlow()

    fun seleccionarIdioma(idioma: Idioma) {
        _estado.update {
            it.copy(idioma = idioma)
        }
    }

    fun seleccionarCategoria(categoriaId: Int) {
        val categoria = DatosVocabulario.categorias.find {
            it.id == categoriaId
        } ?: return

        val palabrasCategoria = FiltroCategorias.filtrar(
            palabras = DatosVocabulario.palabras,
            categoriaId = categoriaId
        )

        _estado.update {
            it.copy(
                categoriaSeleccionada = categoria,
                palabras = palabrasCategoria
            )
        }
    }
}
