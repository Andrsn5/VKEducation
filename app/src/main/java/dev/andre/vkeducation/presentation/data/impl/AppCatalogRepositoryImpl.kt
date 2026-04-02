package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.api.AppCatalogApi
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
class AppCatalogRepositoryImpl(
    private val api: AppCatalogApi,
    private val mapper: AppCatalogMapper
): AppCatalogRepository {
    override suspend fun getAll(): List<AppCatalog> {
        val apps = api.getAll()
        if (apps == null) throw IllegalStateException("Ошибка загрузки")
        return apps.map { mapper.toDomain(it) }
    }
}