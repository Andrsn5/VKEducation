package dev.andre.vkeducation.presentation.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppCatalogDto(
    val id: String,
    val name: String,
    val category: String,
    val iconUrl: String,
    val description: String
)