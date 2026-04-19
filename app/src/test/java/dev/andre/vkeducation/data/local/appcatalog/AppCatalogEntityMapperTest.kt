package dev.andre.vkeducation.data.local.appcatalog

import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntity
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import org.junit.Test
import kotlin.test.assertEquals

class AppCatalogEntityMapperTest {

    private val mapper = AppCatalogEntityMapper()

    private fun createDomain(
        id: String = "1",
        name: String = "App",
        category: Category = Category.ИГРЫ,
        iconUrl: String = "url",
        description: String = "desc",
        isInWishList: Boolean = false
    ) = AppCatalog(
        id = id,
        name = name,
        category = category,
        iconUrl = iconUrl,
        description = description,
        isInWishList = isInWishList
    )

    private fun createEntity(
        id: String = "1",
        name: String = "App",
        category: Category = Category.ИГРЫ,
        iconUrl: String = "url",
        description: String = "desc"
    ) = AppCatalogEntity(
        id = id,
        name = name,
        category = category,
        iconUrl = iconUrl,
        description = description
    )

    @Test
    fun `maps entity to domain`() {
        val entity = createEntity()

        val result = mapper.toDomain(entity)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals(Category.ИГРЫ, result.category)
        assertEquals("url", result.iconUrl)
        assertEquals("desc", result.description)
    }

    @Test
    fun `maps domain to entity`() {
        val domain = createDomain(isInWishList = false)

        val result = mapper.toEntity(domain)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals(Category.ИГРЫ, result.category)
        assertEquals("url", result.iconUrl)
        assertEquals("desc", result.description)
    }


    @Test
    fun `round trip mapping preserves data`() {
        val original = createDomain(
            id = "123",
            name = "Test App",
            category = Category.ФИНАНСЫ,
            iconUrl = "https://example.com/icon.png",
            description = "Test description"
        )

        val entity = mapper.toEntity(original)
        val result = mapper.toDomain(entity)

        assertEquals(original.id, result.id)
        assertEquals(original.name, result.name)
        assertEquals(original.category, result.category)
        assertEquals(original.iconUrl, result.iconUrl)
        assertEquals(original.description, result.description)
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
            val domain = createDomain(category = category)
            val entity = mapper.toEntity(domain)
            val result = mapper.toDomain(entity)

            assertEquals(category, result.category)
        }
    }
}