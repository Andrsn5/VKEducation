package dev.andre.vkeducation.presentation.data.local.appdetails

import dev.andre.vkeducation.presentation.domain.model.App
import javax.inject.Inject

class AppDetailsEntityMapper @Inject constructor() {

    fun toEntity(domain: App) : AppDetailsEntity = AppDetailsEntity(
        id = domain.id,
        name = domain.name,
        developer = domain.developer,
        category = domain.category,
        ageRating = domain.ageRating,
        size = domain.size,
        iconUrl = domain.iconUrl,
        screenshotUrlList = domain.screenshotUrlList,
        description = domain.description,
    )
    
    fun toDomain(entity: AppDetailsEntity) : App = App(
        id = entity.id,
        name = entity.name,
        developer = entity.developer,
        category = entity.category,
        ageRating = entity.ageRating,
        size = entity.size,
        iconUrl = entity.iconUrl,
        screenshotUrlList = entity.screenshotUrlList,
        description = entity.description
    )

}