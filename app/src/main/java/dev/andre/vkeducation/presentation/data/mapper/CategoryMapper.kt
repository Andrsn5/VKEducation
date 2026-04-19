package dev.andre.vkeducation.presentation.data.mapper

import dev.andre.vkeducation.presentation.domain.model.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toDomain(category: String): Category {
        return Category.from(category)
    }
}