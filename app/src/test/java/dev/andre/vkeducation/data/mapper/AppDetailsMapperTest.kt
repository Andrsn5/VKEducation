package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppDetailsDto
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AppDetailsMapperTest {

    private val categoryMapper = CategoryMapper()
    private val mapper = AppDetailsMapper(categoryMapper)

    @Test
    fun `maps dto to domain`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Игры",
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = listOf("s1", "s2"),
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals("Dev", result.developer)
        assertEquals(Category.ИГРЫ, result.category)
        assertEquals(12, result.ageRating)
        assertEquals(50.5f, result.size)
        assertEquals("url", result.iconUrl)
        assertEquals(listOf("s1", "s2"), result.screenshotUrlList)
        assertEquals("desc", result.description)
    }

    @Test
    fun `maps with empty screenshot list`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Образование",
            ageRating = 3,
            size = 25.0f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertEquals(emptyList<String>(), result.screenshotUrlList)
    }

    @Test
    fun `maps with default screenshot list`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Финансы",
            ageRating = 18,
            size = 100.0f,
            iconUrl = "url",
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertEquals(emptyList<String>(), result.screenshotUrlList)
    }

    @Test
    fun `maps all fields correctly`() {
        val dto = AppDetailsDto(
            id = "app123",
            name = "Super App",
            developer = "Super Dev",
            category = "Здоровье и фитнес",
            ageRating = 6,
            size = 75.3f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrlList = listOf("screen1.png", "screen2.png", "screen3.png"),
            description = "A super app description"
        )

        val result = mapper.toDomain(dto)

        assertEquals("app123", result.id)
        assertEquals("Super App", result.name)
        assertEquals("Super Dev", result.developer)
        assertEquals(Category.ЗДОРОВЬЕ_И_ФИТНЕС, result.category)
        assertEquals(6, result.ageRating)
        assertEquals(75.3f, result.size)
        assertEquals("https://example.com/icon.png", result.iconUrl)
        assertEquals(3, result.screenshotUrlList.size)
        assertEquals("A super app description", result.description)
    }

    @Test
    fun `handles unknown category`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "UnknownCategory",
            ageRating = 0,
            size = 0f,
            iconUrl = "",
            description = ""
        )

        val result = mapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
    }
}
