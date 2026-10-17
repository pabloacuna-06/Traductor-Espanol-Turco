package com.example.traductor_espanol_turco.viewmodel

import androidx.lifecycle.ViewModel
import com.example.traductor_espanol_turco.model.DatosVocabulario
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

    fun seleccionarCategoria(categoriaId: Int) {
        val categoria = DatosVocabulario.categorias.find {
            it.id == categoriaId
        } ?: return

        val palabrasCategoria = DatosVocabulario.palabras.filter {
            it.categoriaId == categoriaId
        }

        _estado.update {
            it.copy(
                categoriaSeleccionada = categoria,
                palabras = palabrasCategoria
            )
        }
    }
}