package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import org.junit.Test
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertEquals

class CategoryMapperTest {

    private val mapper = CategoryMapper()

    @Test
    fun `maps all known category to domain`() {
        val expectedMaps = mapOf(
            "Игры" to Category.ИГРЫ,
            "Производительность" to Category.ПРОИЗВОДИТЕЛЬНОСТЬ,
            "Здоровье и фитнес" to Category.ЗДОРОВЬЕ_И_ФИТНЕС,
            "Фото и видео" to Category.ФОТО_И_ВИДЕО,
            "Еда и напитки" to Category.ЕДА_И_НАПИТКИ,
            "Образование" to Category.ОБРАЗОВАНИЕ,
            "Образ жизни" to Category.ОБРАЗ_ЖИЗНИ,
            "Шопинг" to Category.ШОПИНГ,
            "Новости" to Category.НОВОСТИ,
            "Музыка" to Category.МУЗЫКА,
            "Финансы" to Category.ФИНАНСЫ,
            "Утилиты" to Category.УТИЛИТЫ,
            "Навигация" to Category.НАВИГАЦИЯ,
            "Общение" to Category.ОБЩЕНИЕ,
            "Бизнес" to Category.БИЗНЕС,
            "Погода" to Category.ПОГОДА,
            "Развлечения" to Category.РАЗВЛЕЧЕНИЯ,
            "Книги и справочники" to Category.КНИГИ_И_СПРАВОЧНИКИ
        )

        expectedMaps.forEach { (inputValues, expectedValues) ->
            val actual = mapper.toDomain(inputValues)
            assertEquals(expectedValues,actual,"Failed for input: $inputValues")
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
            "игры" to Category.ИГРЫ,
            "ИГРЫ" to Category.ИГРЫ,
            "Игры" to Category.ИГРЫ,
            "иГрЫ" to Category.ИГРЫ
        )

        variations.forEach { (input, expected) ->
            val result = mapper.toDomain(input)
            assertEquals("Category '$input' should map to $expected", expected, result)
        }
    }
}
