package dev.andre.vkeducation.presentation.data.api

import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.data.dto.AppDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/catalog")
    suspend fun getCatalog(): List<AppCatalogDto>?

    @GET("/catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto?
}