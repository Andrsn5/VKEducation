package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AppCatalogMapperTest {

    private val categoryMapper = CategoryMapper()
    private val mapper = AppCatalogMapper(categoryMapper)

    @Test
    fun `maps dto to domain`() {
        val dto = AppCatalogDto(
            id = "1",
            name = "App",
            category = "Игры",
            iconUrl = "url",
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals(Category.ИГРЫ, result.category)
        assertEquals("url", result.iconUrl)
        assertEquals("desc", result.description)
    }

    @Test
    fun `maps all fields correctly`() {
        val dto = AppCatalogDto(
            id = "123",
            name = "Test App",
            category = "Образование",
            iconUrl = "https://example.com/icon.png",
            description = "Test description"
        )

        val result = mapper.toDomain(dto)

        assertEquals("123", result.id)
        assertEquals("Test App", result.name)
        assertEquals(Category.ОБРАЗОВАНИЕ, result.category)
        assertEquals("https://example.com/icon.png", result.iconUrl)
        assertEquals("Test description", result.description)
    }

    @Test
    fun `handles unknown category`() {
        val dto = AppCatalogDto(
            id = "1",
            name = "App",
            category = "UnknownCategory",
            iconUrl = "url",
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
    }

    @Test
    fun `maps different categories correctly`() {
        val categories = listOf(
            "Игры" to Category.ИГРЫ,
            "Финансы" to Category.ФИНАНСЫ,
            "Здоровье и фитнес" to Category.ЗДОРОВЬЕ_И_ФИТНЕС,
            "Образование" to Category.ОБРАЗОВАНИЕ
        )

        categories.forEach { (input, expected) ->
            val dto = AppCatalogDto(
                id = "1",
                name = "App",
                category = input,
                iconUrl = "url",
                description = "desc"
            )

            val result = mapper.toDomain(dto)
            assertEquals(expected, result.category)
        }
    }
}
