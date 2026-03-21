package dev.andre.vkeducation.presentation.domain

data class AppCatalog(
    val id: String,
    val name: String,
    val category: Category,
    val iconUrl: String,
    val description: String
)