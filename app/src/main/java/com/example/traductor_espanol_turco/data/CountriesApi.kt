package com.example.traductor_espanol_turco.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class PaisDto(
    val name: String,
    val alpha2Code: String,
    val capital: String,
    val flags: BanderasDto
)

data class BanderasDto(
    val png: String
)

interface CountriesApi {
    @GET("alpha/{codigo}")
    suspend fun obtenerPais(
        @Path("codigo") codigo: String,
        @Query("fields") fields: String = "name,alpha2Code,capital,flags"
    ): PaisDto
}
