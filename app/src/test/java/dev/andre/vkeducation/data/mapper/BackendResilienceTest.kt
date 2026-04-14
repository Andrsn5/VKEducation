package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.data.dto.AppDetailsDto
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test


class BackendResilienceTest {

    private val categoryMapper = CategoryMapper()
    private val appCatalogMapper = AppCatalogMapper(categoryMapper)
    private val appDetailsMapper = AppDetailsMapper(categoryMapper)

    @Test
    fun `handles unknown category gracefully`() {
        val dto = AppCatalogDto(
            id = "1",
            name = "AI Assistant",
            category = "AI Tools",
            iconUrl = "url",
            description = "desc"
        )

        val result = appCatalogMapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
        assertEquals("AI Assistant", result.name)
    }

    @Test
    fun `handles empty category string`() {
        val dto = AppCatalogDto(
            id = "1",
            name = "App",
            category = "",
            iconUrl = "url",
            description = "desc"
        )

        val result = appCatalogMapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
    }

    @Test
    fun `preserves data when category is unknown`() {
        val dto = AppCatalogDto(
            id = "123",
            name = "Test App",
            category = "Future Category",
            iconUrl = "https://example.com/icon.png",
            description = "Important description"
        )

        val result = appCatalogMapper.toDomain(dto)

        assertEquals("123", result.id)
        assertEquals("Test App", result.name)
        assertEquals("https://example.com/icon.png", result.iconUrl)
        assertEquals("Important description", result.description)
        assertEquals(Category.UNKNOWN, result.category)
    }

    @Test
    fun `handles negative age rating`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Игры",
            ageRating = -1,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = appDetailsMapper.toDomain(dto)

        assertEquals(-1, result.ageRating)
    }

    @Test
    fun `handles zero size`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Игры",
            ageRating = 12,
            size = 0f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = appDetailsMapper.toDomain(dto)

        assertEquals(0f, result.size)
    }

    @Test
    fun `handles extremely large size`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Игры",
            ageRating = 12,
            size = 999999999f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = appDetailsMapper.toDomain(dto)

        assertEquals(999999999f, result.size)
    }

    @Test
    fun `handles empty screenshot list`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Игры",
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = appDetailsMapper.toDomain(dto)

        assertTrue(result.screenshotUrlList.isEmpty())
    }



    @Test
    fun `handles special characters in strings`() {
        val dto = AppCatalogDto(
            id = "1",
            name = "App <script>alert('xss')</script>",
            category = "Игры",
            iconUrl = "url",
            description = "Desc with emoji 🎮 and unicode"
        )

        val result = appCatalogMapper.toDomain(dto)


        assertEquals("App <script>alert('xss')</script>", result.name)
        assertEquals("Desc with emoji 🎮 and unicode", result.description)
    }

    @Test
    fun `handles very long strings`() {
        val longString = "a".repeat(10000)
        val dto = AppCatalogDto(
            id = "1",
            name = longString,
            category = "Игры",
            iconUrl = "url",
            description = longString
        )

        val result = appCatalogMapper.toDomain(dto)

        assertEquals(10000, result.name.length)
        assertEquals(10000, result.description.length)
    }

    @Test
    fun `isInWishList defaults to false in domain`() {
        val dto = AppCatalogDto(
            id = "1",
            name = "App",
            category = "Игры",
            iconUrl = "url",
            description = "desc"
        )

        val result = appCatalogMapper.toDomain(dto)

        assertEquals(false, result.isInWishList)
    }

    @Test
    fun `handles all known categories correctly`() {
        val knownCategories = mapOf(
            "Игры" to Category.ИГРЫ,
            "Образование" to Category.ОБРАЗОВАНИЕ,
            "Финансы" to Category.ФИНАНСЫ,
            "Здоровье и фитнес" to Category.ЗДОРОВЬЕ_И_ФИТНЕС,
            "Фото и видео" to Category.ФОТО_И_ВИДЕО,
            "Еда и напитки" to Category.ЕДА_И_НАПИТКИ,
            "Образ жизни" to Category.ОБРАЗ_ЖИЗНИ,
            "Шопинг" to Category.ШОПИНГ,
            "Новости" to Category.НОВОСТИ,
            "Музыка" to Category.МУЗЫКА,
            "Утилиты" to Category.УТИЛИТЫ,
            "Навигация" to Category.НАВИГАЦИЯ,
            "Общение" to Category.ОБЩЕНИЕ,
            "Бизнес" to Category.БИЗНЕС,
            "Погода" to Category.ПОГОДА,
            "Развлечения" to Category.РАЗВЛЕЧЕНИЯ,
            "Книги и справочники" to Category.КНИГИ_И_СПРАВОЧНИКИ,
            "Производительность" to Category.ПРОИЗВОДИТЕЛЬНОСТЬ
        )

        knownCategories.forEach { (backendValue, expectedCategory) ->
            val dto = AppCatalogDto(
                id = "1",
                name = "App",
                category = backendValue,
                iconUrl = "url",
                description = "desc"
            )

            val result = appCatalogMapper.toDomain(dto)
            assertEquals(
                "Category '$backendValue' should map to $expectedCategory",
                expectedCategory,
                result.category
            )
        }
    }

    @Test
    fun `handles case sensitivity in category names`() {
        val variations = listOf(
            "игры" to Category.ИГРЫ,
            "ИГРЫ" to Category.ИГРЫ,
            "Игры" to Category.ИГРЫ,
            "иГрЫ" to Category.ИГРЫ
        )

        variations.forEach { (input, expected) ->
            val dto = AppCatalogDto(
                id = "1",
                name = "App",
                category = input,
                iconUrl = "url",
                description = "desc"
            )

            val result = appCatalogMapper.toDomain(dto)
            assertEquals("Category '$input' should map to $expected", expected, result.category)
        }
    }

    @Test
    fun `AppDetailsMapper handles unknown category`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "New Category",
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = appDetailsMapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals("Dev", result.developer)
    }

    @Test
    fun `AppDetailsMapper defaults isInWishList to false`() {
        val dto = AppDetailsDto(
            id = "1",
            name = "App",
            developer = "Dev",
            category = "Игры",
            ageRating = 12,
            size = 50.5f,
            iconUrl = "url",
            screenshotUrlList = emptyList(),
            description = "desc"
        )

        val result = appDetailsMapper.toDomain(dto)

        assertEquals(false, result.isInWishList)
    }
}
