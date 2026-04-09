package dev.andre.vkeducation.presentation.data.local

import androidx.room.TypeConverter
import dev.andre.vkeducation.presentation.domain.model.Category

class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(name: String): Category = Category.valueOf(name)
}