package dev.andre.vkeducation.presentation.data

import dev.andre.vkeducation.presentation.domain.Category


class CategoryMapper {
    fun toDomain(category: String): Category{
        return when (category) {
            "APP" -> Category.APP
            "GAME" -> Category.GAME
            else -> throw IllegalArgumentException("Invalid category: $category")
        }
    }
}