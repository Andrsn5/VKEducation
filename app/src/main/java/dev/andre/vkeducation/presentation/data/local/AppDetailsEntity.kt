package dev.andre.vkeducation.presentation.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.andre.vkeducation.presentation.domain.model.Category

@Entity(tableName = "app_details")
data class AppDetailsEntity(
    @PrimaryKey val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String> = emptyList(),
    val description: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
