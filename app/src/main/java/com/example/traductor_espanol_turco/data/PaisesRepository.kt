package com.example.traductor_espanol_turco.data

import com.example.traductor_espanol_turco.model.PaisInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface PaisesRepository {
    suspend fun obtenerChileYTurquia(): List<PaisInfo>
}

class CountriesDevRepository(
    private val api: CountriesApi = crearApi()
) : PaisesRepository {

    override suspend fun obtenerChileYTurquia(): List<PaisInfo> {
        return listOf("CL", "TR").map { codigo ->
            val pais = api.obtenerPais(codigo)
            PaisInfo(
                codigo = pais.alpha2Code,
                nombre = if (codigo == "TR") "Turquía" else "Chile",
                capital = pais.capital,
                banderaUrl = pais.flags.png
            )
        }
    }

    companion object {
        private fun crearApi(): CountriesApi = Retrofit.Builder()
            .baseUrl("https://countries.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}
