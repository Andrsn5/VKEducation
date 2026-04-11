package dev.andre.vkeducation.presentation.domain.repository

import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import kotlinx.coroutines.flow.Flow

interface AppCatalogRepository {
    suspend fun getAll()

    suspend fun observeAppCatalog() : Flow<List<AppCatalog>>

    suspend fun toggleWishList(id: String)
}