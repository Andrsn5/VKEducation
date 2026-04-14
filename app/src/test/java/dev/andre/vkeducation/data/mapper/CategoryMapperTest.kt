package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CategoryMapperTest {

    private val mapper = CategoryMapper()

    @Test
    fun `maps GAME to ИГРЫ`() {
        assertEquals(Category.ИГРЫ, mapper.toDomain("Игры"))
    }

    @Test
    fun `maps PERFORMANCE to ПРОИЗВОДИТЕЛЬНОСТЬ`() {
        assertEquals(Category.ПРОИЗВОДИТЕЛЬНОСТЬ, mapper.toDomain("Производительность"))
    }

    @Test
    fun `maps HEALTH to ЗДОРОВЬЕ_И_ФИТНЕС`() {
        assertEquals(Category.ЗДОРОВЬЕ_И_ФИТНЕС, mapper.toDomain("Здоровье и фитнес"))
    }

    @Test
    fun `maps PHOTO to ФОТО_И_ВИДЕО`() {
        assertEquals(Category.ФОТО_И_ВИДЕО, mapper.toDomain("Фото и видео"))
    }

    @Test
    fun `maps FOOD to ЕДА_И_НАПИТКИ`() {
        assertEquals(Category.ЕДА_И_НАПИТКИ, mapper.toDomain("Еда и напитки"))
    }

    @Test
    fun `maps EDUCATION to ОБРАЗОВАНИЕ`() {
        assertEquals(Category.ОБРАЗОВАНИЕ, mapper.toDomain("Образование"))
    }

    @Test
    fun `maps LIFESTYLE to ОБРАЗ_ЖИЗНИ`() {
        assertEquals(Category.ОБРАЗ_ЖИЗНИ, mapper.toDomain("Образ жизни"))
    }

    @Test
    fun `maps SHOPPING to ШОПИНГ`() {
        assertEquals(Category.ШОПИНГ, mapper.toDomain("Шопинг"))
    }

    @Test
    fun `maps NEWS to НОВОСТИ`() {
        assertEquals(Category.НОВОСТИ, mapper.toDomain("Новости"))
    }

    @Test
    fun `maps MUSIC to МУЗЫКА`() {
        assertEquals(Category.МУЗЫКА, mapper.toDomain("Музыка"))
    }

    @Test
    fun `maps FINANCE to ФИНАНСЫ`() {
        assertEquals(Category.ФИНАНСЫ, mapper.toDomain("Финансы"))
    }

    @Test
    fun `maps UTILITIES to УТИЛИТЫ`() {
        assertEquals(Category.УТИЛИТЫ, mapper.toDomain("Утилиты"))
    }

    @Test
    fun `maps NAVIGATION to НАВИГАЦИЯ`() {
        assertEquals(Category.НАВИГАЦИЯ, mapper.toDomain("Навигация"))
    }

    @Test
    fun `maps SOCIAL to ОБЩЕНИЕ`() {
        assertEquals(Category.ОБЩЕНИЕ, mapper.toDomain("Общение"))
    }

    @Test
    fun `maps BUSINESS to БИЗНЕС`() {
        assertEquals(Category.БИЗНЕС, mapper.toDomain("Бизнес"))
    }

    @Test
    fun `maps WEATHER to ПОГОДА`() {
        assertEquals(Category.ПОГОДА, mapper.toDomain("Погода"))
    }

    @Test
    fun `maps ENTERTAINMENT to РАЗВЛЕЧЕНИЯ`() {
        assertEquals(Category.РАЗВЛЕЧЕНИЯ, mapper.toDomain("Развлечения"))
    }

    @Test
    fun `maps BOOKS to КНИГИ_И_СПРАВОЧНИКИ`() {
        assertEquals(Category.КНИГИ_И_СПРАВОЧНИКИ, mapper.toDomain("Книги и справочники"))
    }

    @Test
    fun `maps unknown string to UNKNOWN`() {
        assertEquals(Category.UNKNOWN, mapper.toDomain("UnknownCategory"))
    }

    @Test
    fun `maps empty string to UNKNOWN`() {
        assertEquals(Category.UNKNOWN, mapper.toDomain(""))
    }
}
