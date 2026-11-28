package com.example.traductor_espanol_turco.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traductor_espanol_turco.data.CountriesDevRepository
import com.example.traductor_espanol_turco.data.PaisesRepository
import com.example.traductor_espanol_turco.model.ConocePaisesUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConocePaisesViewModel(
    private val repositorio: PaisesRepository = CountriesDevRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(ConocePaisesUiState())
    val estado: StateFlow<ConocePaisesUiState> = _estado.asStateFlow()

    init {
        cargarPaises()
    }

    fun reintentar() {
        cargarPaises()
    }

    private fun cargarPaises() {
        if (_estado.value.cargando) return

        viewModelScope.launch {
            _estado.value = ConocePaisesUiState(cargando = true)

            try {
                val paises = repositorio.obtenerChileYTurquia()
                _estado.value = ConocePaisesUiState(paises = paises)
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _estado.value = ConocePaisesUiState(hayError = true)
            }
        }
    }
}
