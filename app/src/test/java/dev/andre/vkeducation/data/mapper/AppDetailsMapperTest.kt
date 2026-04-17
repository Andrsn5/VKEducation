package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppDetailsDto
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AppDetailsMapperTest {

    private val categoryMapper = CategoryMapper()
    private val mapper = AppDetailsMapper(categoryMapper)

    private fun createDto(
        id: String = "1",
        name: String = "App",
        developer: String = "Dev",
        category: String = "Игры",
        ageRating: Int = 12,
        size: Float = 50.5f,
        iconUrl: String = "url",
        screenshotUrlList: List<String> = listOf("s1", "s2"),
        description: String = "desc"
    ) = AppDetailsDto(
        id = id,
        name = name,
        developer = developer,
        category = category,
        ageRating = ageRating,
        size = size,
        iconUrl = iconUrl,
        screenshotUrlList = screenshotUrlList,
        description = description
    )

    @Test
    fun `maps dto to domain`() {
        val dto = createDto()

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
        val dto = createDto(
            screenshotUrlList = emptyList()
        )

        val result = mapper.toDomain(dto)

        assertEquals(emptyList(), result.screenshotUrlList)
    }

    @Test
    fun `handles unknown category`() {
        val dto = createDto(
            category = "UnknownCategory"
        )

        val result = mapper.toDomain(dto)

        assertEquals(Category.UNKNOWN, result.category)
    }

    @Test
    fun `handles negative age rating`() {
        val dto = createDto(ageRating = -1)

        val result = mapper.toDomain(dto)

        assertEquals(-1, result.ageRating)
    }

    @Test
    fun `handles zero size`() {
        val dto = createDto(size = 0f)

        val result = mapper.toDomain(dto)

        assertEquals(0f, result.size)
    }

    @Test
    fun `handles extremely large size`() {
        val dto = createDto(size = 999999999f)

        val result = mapper.toDomain(dto)

        assertEquals(999999999f, result.size)
    }

    @Test
    fun `AppDetailsMapper defaults isInWishList to false`() {
        val dto = createDto()

        val result = mapper.toDomain(dto)

        assertFalse(result.isInWishList)
    }
}