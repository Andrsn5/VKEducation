package dev.andre.vkeducation.presentation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import javax.inject.Inject

class AppCatalogMapper @Inject constructor (
    private val categoryMapper: CategoryMapper
){
    fun toDomain(appCatalogDto : AppCatalogDto) = AppCatalog(
        id = appCatalogDto.id,
        name = appCatalogDto.name,
        category = categoryMapper.toDomain(appCatalogDto.category),
        iconUrl = appCatalogDto.iconUrl,
        description = appCatalogDto.description
    )
}