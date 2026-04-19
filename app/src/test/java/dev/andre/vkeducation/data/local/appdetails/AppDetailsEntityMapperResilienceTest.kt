package dev.andre.vkeducation.data.local.appdetails

import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntity
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntityMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test


class AppDetailsEntityMapperResilienceTest {

    private val mapper = AppDetailsEntityMapper()

    @Test
    fun `domain to entity drops isInWishList and lastUpdated`() {
        val domain = App(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = listOf("s1", "s2"),
            isInWishList = true,
            description = "desc"
        )

        val entity = mapper.toEntity(domain)

        assertEquals("1", entity.id)
        assertEquals("App", entity.name)
        assertEquals("Dev", entity.developer)
        assertEquals(12, entity.ageRating)
        assertEquals(50.5f, entity.size)
        assertEquals(listOf("s1", "s2"), entity.screenshotUrlList)
    }

    @Test
    fun `entity to domain drops lastUpdated`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc",
            lastUpdated = 123456789L
        )

        val domain = mapper.toDomain(entity)


        assertEquals("1", domain.id)
        assertEquals("App", domain.name)
        assertEquals("Dev", domain.developer)
        assertFalse(domain.isInWishList)
    }

    @Test
    fun `handles negative age rating in entity`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = -1,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val domain = mapper.toDomain(entity)

        assertEquals(-1, domain.ageRating)
    }

    @Test
    fun `handles zero size in entity`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = 12,
            size = 0f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val domain = mapper.toDomain(entity)

        assertEquals(0f, domain.size)
    }

    @Test
    fun `handles empty screenshot list in entity`() {
        val entity = AppDetailsEntity(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val domain = mapper.toDomain(entity)

        assertTrue(domain.screenshotUrlList.isEmpty())
    }

    @Test
    fun `handles null URLs in entity`(){
        val domain = App(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = 12,
            size = 50.5f,
            iconUrl = "",
            screenshotUrlList = emptyList(),
            isInWishList = false,
            description = ""
        )

        val entity = mapper.toEntity(domain)

        assertEquals("", entity.iconUrl)
    }

    @Test
    fun `round trip preserves all fields except isInWishList and lastUpdated`() {
        val original = App(
            id = "app123",
            name = "Super App",
            developer = "Super Dev",
            category = Category.FINANCE,
            ageRating = 18,
            size = 100.0f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrlList = listOf("screen1.png", "screen2.png"),
            isInWishList = true,
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

        assertFalse(result.isInWishList)
    }

    @Test
    fun `handles all categories in entity`() {
        Category.entries.forEach { category ->
            val domain = App(
                id = "1",
                name = "App",
                developer = "Dev",
                category = category,
                ageRating = 12,
                size = 50.0f,
                iconUrl = "url",
                screenshotUrlList = emptyList(),
                isInWishList = false,
                description = "desc"
            )

            val entity = mapper.toEntity(domain)
            val result = mapper.toDomain(entity)

            assertEquals(category, result.category)
        }
    }

    @Test
    fun `handles large screenshot list in entity`() {
        val largeList = (1..1000).map { "screen$it.png" }

        val domain = App(
            id = "1",
            name = "App",
            developer = "Dev",
            category = Category.GAMES,
            ageRating = 12,
            size = 50.0f,
            iconUrl = "url",
            screenshotUrlList = largeList,
            isInWishList = false,
            description = "desc"
        )

        val entity = mapper.toEntity(domain)
        val result = mapper.toDomain(entity)

        assertEquals(1000, result.screenshotUrlList.size)
    }

    @Test
    fun `entity mapper does not validate data`() {
        val invalidDomain = App(
            id = "",
            name = "",
            developer = "",
            category = Category.UNKNOWN,
            ageRating = -999,
            size = -1f,
            iconUrl = "not-a-url",
            screenshotUrlList = listOf("", "not-a-url"),
            isInWishList = false,
            description = ""
        )

        val entity = mapper.toEntity(invalidDomain)

        assertEquals("", entity.id)
        assertEquals(-999, entity.ageRating)
        assertEquals(-1f, entity.size)
    }
}
