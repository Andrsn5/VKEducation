package dev.andre.vkeducation.data.local.appcatalog

import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntity
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Test


class EntityMapperResilienceTest {

    private val mapper = AppCatalogEntityMapper()

    @Test
    fun `domain to entity drops isInWishList field`() {
        val domain = AppCatalog(
            id = "1",
            name = "App",
            category = Category.ИГРЫ,
            iconUrl = "url",
            description = "desc",
            isInWishList = true
        )

        val entity = mapper.toEntity(domain)

        assertEquals("1", entity.id)
        assertEquals("App", entity.name)
        assertEquals(Category.ИГРЫ, entity.category)
    }

    @Test
    fun `entity to domain defaults isInWishList to false`() {

        val entity = AppCatalogEntity(
            id = "1",
            name = "App",
            category = Category.ИГРЫ,
            iconUrl = "url",
            description = "desc"
        )

        val domain = mapper.toDomain(entity)

        assertEquals(false, domain.isInWishList)
    }

    @Test
    fun `handles unknown category in entity`() {
        val entity = AppCatalogEntity(
            id = "1",
            name = "App",
            category = Category.UNKNOWN,
            iconUrl = "url",
            description = "desc"
        )

        val domain = mapper.toDomain(entity)

        assertEquals(Category.UNKNOWN, domain.category)
    }

    @Test
    fun `round trip preserves all fields except isInWishList`() {
        val original = AppCatalog(
            id = "123",
            name = "Test App",
            category = Category.ФИНАНСЫ,
            iconUrl = "https://example.com/icon.png",
            description = "Test description",
            isInWishList = true
        )

        val entity = mapper.toEntity(original)
        val result = mapper.toDomain(entity)

        assertEquals(original.id, result.id)
        assertEquals(original.name, result.name)
        assertEquals(original.category, result.category)
        assertEquals(original.iconUrl, result.iconUrl)
        assertEquals(original.description, result.description)

        assertFalse(result.isInWishList)
    }

    @Test
    fun `handles empty strings in entity`() {
        val entity = AppCatalogEntity(
            id = "",
            name = "",
            category = Category.ИГРЫ,
            iconUrl = "",
            description = ""
        )

        val domain = mapper.toDomain(entity)

        assertEquals("", domain.id)
        assertEquals("", domain.name)
        assertEquals("", domain.iconUrl)
        assertEquals("", domain.description)
    }

    @Test
    fun `handles all categories in entity`() {
        Category.entries.forEach { category ->
            val entity = AppCatalogEntity(
                id = "1",
                name = "App",
                category = category,
                iconUrl = "url",
                description = "desc"
            )

            val domain = mapper.toDomain(entity)

            assertEquals(category, domain.category)
        }
    }

    @Test
    fun `entity mapper does not validate data`() {
        val invalidDomain = AppCatalog(
            id = "",
            name = "",
            category = Category.UNKNOWN,
            iconUrl = "not-a-url",
            description = "",
            isInWishList = false
        )

        val entity = mapper.toEntity(invalidDomain)

        assertEquals("", entity.id)
        assertEquals("", entity.name)
        assertEquals("not-a-url", entity.iconUrl)
    }
}
