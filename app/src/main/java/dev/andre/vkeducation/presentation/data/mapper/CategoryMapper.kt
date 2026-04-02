package dev.andre.vkeducation.presentation.data.mapper

import dev.andre.vkeducation.presentation.domain.model.Category

class CategoryMapper  {
    fun toDomain(category: String): Category {
        return Category.entries
            .firstOrNull{it.name == category}
            ?: throw IllegalArgumentException("Invalid category: $category")
    }
}