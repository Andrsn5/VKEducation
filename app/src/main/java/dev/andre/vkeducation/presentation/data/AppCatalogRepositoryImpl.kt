package dev.andre.vkeducation.presentation.data

import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.domain.AppCatalog
import dev.andre.vkeducation.presentation.domain.AppCatalogRepository

class AppCatalogRepositoryImpl(
    private val api: AppCatalogApi,
    private val mapper: AppCatalogMapper
): AppCatalogRepository {
    override suspend fun getAll(): List<AppCatalog> {
        val apps = api.getAll()
        if (apps == null) throw IllegalStateException("${R.string.app_catalog_no_loaded}")
        return apps.map { mapper.toDomain(it) }
    }
}