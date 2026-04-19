package dev.andre.vkeducation.presentation.data.local.appcatalog

import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import javax.inject.Inject

class AppCatalogEntityMapper @Inject constructor() {
    fun toDomain(entity: AppCatalogEntity) : AppCatalog = AppCatalog(
        id = entity.id,
        name = entity.name,
        category = entity.category,
        iconUrl = entity.iconUrl,
        description = entity.description,
    )

    fun toEntity(domain: AppCatalog) : AppCatalogEntity = AppCatalogEntity(
        id = domain.id,
        name = domain.name,
        category = domain.category,
        iconUrl = domain.iconUrl,
        description = domain.description
    )


}