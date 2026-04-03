package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import javax.inject.Inject

class AppCatalogRepositoryImpl @Inject constructor (
    private val api: ApiService,
    private val mapper: AppCatalogMapper
): AppCatalogRepository {
    override suspend fun getAll(): List<AppCatalog> {
        val apps = api.getCatalog()
        if (apps == null) throw IllegalStateException("Ошибка загрузки")
        return apps.map { mapper.toDomain(it) }
    }
}