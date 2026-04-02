package dev.andre.vkeducation.presentation.domain.repository

import dev.andre.vkeducation.presentation.domain.model.AppCatalog

interface AppCatalogRepository {
    suspend fun getAll() : List<AppCatalog>
}