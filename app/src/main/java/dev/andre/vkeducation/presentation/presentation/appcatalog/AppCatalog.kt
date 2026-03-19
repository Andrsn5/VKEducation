package dev.andre.vkeducation.presentation.presentation.appcatalog

import dev.andre.vkeducation.presentation.presentation.appdetails.Category

data class AppCatalog(
    val name: String,
    val category: Category,
    val iconUrl: String,
    val description: String
)