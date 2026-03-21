package dev.andre.vkeducation.presentation.data

import dev.andre.vkeducation.presentation.domain.Category

data class AppCatalogDto(
    val id: String,
    val name: String,
    val category: String,
    val iconUrl: String,
    val description: String
)