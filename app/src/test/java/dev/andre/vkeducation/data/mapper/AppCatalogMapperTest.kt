package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.test.assertFalse

class AppCatalogMapperTest {

    private val categoryMapper = CategoryMapper()
    private val mapper = AppCatalogMapper(categoryMapper)

    private fun createCatalogApp(
        id: String = "1",
        name: String = "App",
        category: String = "Игры",
        iconUrl: String = "url",
        description: String = "desc"
    ) = AppCatalogDto(
        id = id,
        name = name,
        category = category,
        iconUrl = iconUrl,
        description = description
    )

    @Test
    fun `maps dto to domain`() {
        val dto = createCatalogApp()

        val result = mapper.toDomain(dto)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals(Category.GAMES, result.category)
        assertEquals("url", result.iconUrl)
        assertEquals("desc", result.description)
    }

    @Test
    fun `handles unknown category`() {
        val dto = createCatalogApp(
            category = "UnknownCategory",
        )

        val result = mapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
    }

    @Test
    fun `maps different categories correctly`() {
        val categories = listOf(
            "Игры" to Category.GAMES,
            "Финансы" to Category.FINANCE,
            "Здоровье и фитнес" to Category.HEALTH_AND_FITNESS,
            "Образование" to Category.EDUCATION
        )

        categories.forEach { (input, expected) ->
            val dto = createCatalogApp(
                category = input,
            )

            val result = mapper.toDomain(dto)
            assertEquals(expected, result.category)
        }
    }

    @Test
    fun `AppCatalogMapper defaults isInWishList to false`() {
        val dto = createCatalogApp()

        val result = mapper.toDomain(dto)

        assertFalse(result.isInWishList)
    }

    @Test
    fun `handles special characters in strings`() {
        val dto = createCatalogApp(
            name = "App <script>alert('xss')</script>",
            description = "Desc with emoji 🎮 and unicode"
        )

        val result = mapper.toDomain(dto)

        kotlin.test.assertEquals("App <script>alert('xss')</script>", result.name)
        kotlin.test.assertEquals("Desc with emoji 🎮 and unicode", result.description)
    }

    @Test
    fun `handles very long strings`() {
        val longString = "a".repeat(10000)
        val dto = createCatalogApp(
            name = longString,
            description = longString
        )

        val result = mapper.toDomain(dto)

        kotlin.test.assertEquals(10000, result.name.length)
        kotlin.test.assertEquals(10000, result.description.length)
    }
}
