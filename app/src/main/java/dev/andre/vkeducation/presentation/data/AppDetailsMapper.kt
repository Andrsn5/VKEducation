package dev.andre.vkeducation.presentation.data

import dev.andre.vkeducation.presentation.domain.App

class AppDetailsMapper(
    private val categoryMapper: CategoryMapper
) {
    fun toDomain(appDetails: AppDetailsDto) = App(
        id = appDetails.id,
        name = appDetails.name,
        developer = appDetails.developer,
        category = categoryMapper.toDomain(appDetails.category),
        ageRating = appDetails.ageRating,
        size = appDetails.size,
        iconUrl = appDetails.iconUrl,
        screenshotUrlList = appDetails.screenshotUrlList,
        description = appDetails.description,
    )
}