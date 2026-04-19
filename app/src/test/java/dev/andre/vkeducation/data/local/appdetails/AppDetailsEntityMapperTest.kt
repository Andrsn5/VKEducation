package dev.andre.vkeducation.data.local.appdetails

import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntity
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntityMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AppDetailsEntityMapperTest {

    private val mapper = AppDetailsEntityMapper()

    @Test
    fun `maps entity to domain`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.ИГРЫ,
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = listOf("s1", "s2"),
            description = "desc"
        )

        val result = mapper.toDomain(entity)

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
    fun `maps domain to entity`() {
        val domain = App(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.ИГРЫ,
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = listOf("s1", "s2"),
            description = "desc"
        )

        val result = mapper.toEntity(domain)

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
    fun `entity to domain ignores lastUpdated`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.ОБРАЗОВАНИЕ,
            ageRating = 3,
            size = 25.0f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc",
            lastUpdated = 123456789L
        )

        val result = mapper.toDomain(entity)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals("Dev", result.developer)
        assertEquals(Category.ОБРАЗОВАНИЕ, result.category)
        assertEquals(3, result.ageRating)
        assertEquals(25.0f, result.size)
        assertEquals("url", result.iconUrl)
        assertEquals(emptyList<String>(), result.screenshotUrlList)
        assertEquals("desc", result.description)
    }

    @Test
    fun `round trip mapping preserves data`() {
        val original = App(
            id = "app123",
            name = "Super App",
            developer = "Super Dev",
            category = Category.ФИНАНСЫ,
            ageRating = 18,
            size = 100.0f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrlList = listOf("screen1.png", "screen2.png"),
            description = "A super app description"
        )

        val entity = mapper.toEntity(original)
        val result = mapper.toDomain(entity)

        assertEquals(original.id, result.id)
        assertEquals(original.name, result.name)
        assertEquals(original.developer, result.developer)
        assertEquals(original.category, result.category)
        assertEquals(original.ageRating, result.ageRating)
        assertEquals(original.size, result.size)
        assertEquals(original.iconUrl, result.iconUrl)
        assertEquals(original.screenshotUrlList, result.screenshotUrlList)
        assertEquals(original.description, result.description)
    }

    @Test
    fun `maps with empty screenshot list`() {
        val domain = App(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.ЗДОРОВЬЕ_И_ФИТНЕС,
            ageRating = 6,
            size = 75.3f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = mapper.toEntity(domain)

        assertEquals(emptyList<String>(), result.screenshotUrlList)
    }

    @Test
    fun `maps all categories correctly`() {
        val categories = listOf(
            Category.ИГРЫ,
            Category.ОБРАЗОВАНИЕ,
            Category.ФИНАНСЫ,
            Category.ЗДОРОВЬЕ_И_ФИТНЕС,
            Category.НОВОСТИ
        )

        categories.forEach { category ->
            val domain = App(
                id = "1",
                name = "App",
                developer = "Dev",
                category = category,
                ageRating = 12,
                size = 50.0f,
                iconUrl = "url",
                screenshotUrlList = emptyList(),
                description = "desc"
            )

            val entity = mapper.toEntity(domain)
            val result = mapper.toDomain(entity)

            assertEquals(category, result.category)
        }
    }
}
