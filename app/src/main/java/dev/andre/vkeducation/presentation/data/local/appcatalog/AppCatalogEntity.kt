package dev.andre.vkeducation.presentation.data.local.appcatalog

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.andre.vkeducation.presentation.domain.model.Category

@Entity(tableName = "app_catalog")
data class AppCatalogEntity (
    @PrimaryKey val id: String,
    val name: String,
    val category: Category,
    val iconUrl: String,
    val description: String,
)