package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.test.assertEquals

class CategoryMapperTest {

    private val mapper = CategoryMapper()

    @Test
    fun `maps all known category to domain`() {
        val expectedMaps = mapOf(
            "Игры" to Category.GAMES,
            "Производительность" to Category.PRODUCTIVITY,
            "Здоровье и фитнес" to Category.HEALTH_AND_FITNESS,
            "Фото и видео" to Category.PHOTO_AND_VIDEO,
            "Еда и напитки" to Category.FOOD_AND_DRINKS,
            "Образование" to Category.EDUCATION,
            "Образ жизни" to Category.LIFESTYLE,
            "Шопинг" to Category.SHOPPING,
            "Новости" to Category.NEWS,
            "Музыка" to Category.MUSIC,
            "Финансы" to Category.FINANCE,
            "Утилиты" to Category.UTILITIES,
            "Навигация" to Category.NAVIGATION,
            "Общение" to Category.COMMUNICATION,
            "Бизнес" to Category.BUSINESS,
            "Погода" to Category.WEATHER,
            "Развлечения" to Category.ENTERTAINMENT,
            "Книги и справочники" to Category.BOOKS_AND_REFERENCE
        )

        expectedMaps.forEach { (inputValues, expectedValues) ->
            val actual = mapper.toDomain(inputValues)
            assertEquals(expectedValues, actual, "Failed for input: $inputValues")
        }
    }

    @Test
    fun `maps unknown input to UNKNOWN`() {
        val invalidInputs = listOf(
            "UnknownCategory",
            "",
            "   ",
            "Какой-то вымышленный жанр"
        )

        invalidInputs.forEach { input ->
            val actual = mapper.toDomain(input)
            assertEquals(Category.UNKNOWN, actual, "Failed for input: $input")
        }
    }

    @Test
    fun `handles case sensitivity in category names`() {
        val variations = listOf(
            "игры" to Category.GAMES,
            "ИГРЫ" to Category.GAMES,
            "Игры" to Category.GAMES,
            "иГрЫ" to Category.GAMES
        )

        variations.forEach { (input, expected) ->
            val result = mapper.toDomain(input)
            assertEquals("Category '$input' should map to $expected", expected, result)
        }
    }
}
