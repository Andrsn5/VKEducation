package dev.andre.vkeducation.presentation.data

import dev.andre.vkeducation.presentation.domain.AppCatalog

class AppCatalogMapper(
    private val categoryMapper: CategoryMapper
) {
    fun toDomain(appCatalogDto : AppCatalogDto) = AppCatalog(
        id = appCatalogDto.id,
        name = appCatalogDto.name,
        category = categoryMapper.toDomain(appCatalogDto.category),
        iconUrl = appCatalogDto.iconUrl,
        description = appCatalogDto.description
    )
}