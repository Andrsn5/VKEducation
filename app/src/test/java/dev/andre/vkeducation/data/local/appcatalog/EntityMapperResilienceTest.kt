package dev.andre.vkeducation.data.local.appcatalog

import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntity
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class EntityMapperResilienceTest {

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
    fun `entity to domain defaults isInWishList to false`() {
        val entity = createEntity()

        val domain = mapper.toDomain(entity)

        assertFalse(domain.isInWishList)
    }

    @Test
    fun `handles unknown category in entity`() {
        val entity = createEntity(category = Category.UNKNOWN)

        val domain = mapper.toDomain(entity)

        assertEquals(Category.UNKNOWN, domain.category)
    }

    @Test
    fun `round trip preserves all fields except isInWishList`() {
        val original = createDomain(
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
    fun `handles all categories in entity`() {
        Category.entries.forEach { category ->
            val entity = createEntity(category = category)
            val domain = mapper.toDomain(entity)

            assertEquals(category, domain.category)
        }
    }

    @Test
    fun `entity mapper does not validate data`() {
        val invalidDomain = createDomain(
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