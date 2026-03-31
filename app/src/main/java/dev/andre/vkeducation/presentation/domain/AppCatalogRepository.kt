package dev.andre.vkeducation.presentation.domain

interface AppCatalogRepository {
    suspend fun getAll() : List<AppCatalog>
}