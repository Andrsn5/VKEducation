package dev.andre.vkeducation.data.mapper

import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.data.dto.AppDetailsDto
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SerializationResilienceTest {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Test
    fun `parses valid AppCatalogDto`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "category": "Игры",
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
        assertEquals("Игры", result.category)
        assertEquals("url", result.iconUrl)
        assertEquals("desc", result.description)
    }

    @Test
    fun `parses AppCatalogDto with extra fields`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "category": "Игры",
                "iconUrl": "url",
                "description": "desc",
                "newField": "value",
                "anotherField": 123
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertEquals("1", result.id)
        assertEquals("App", result.name)
    }

    @Test
    fun `parses AppCatalogDto with missing optional-like fields`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "category": "Игры",
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertNotNull(result)
        assertEquals("1", result.id)
    }

    @Test
    fun `parses AppDetailsDto with empty screenshot list`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 12,
                "size": 50.5,
                "iconUrl": "url",
                "screenshotUrlList": [],
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertTrue(result.screenshotUrlList.isEmpty())
    }

    @Test
    fun `parses AppDetailsDto without screenshot list field`() {
        // Backend omits optional field
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 12,
                "size": 50.5,
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertTrue(result.screenshotUrlList.isEmpty())
    }

    @Test
    fun `parses AppDetailsDto with null screenshot list`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 12,
                "size": 50.5,
                "iconUrl": "url",
                "screenshotUrlList": null,
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertTrue(result.screenshotUrlList.isEmpty())
    }

    @Test
    fun `parses with whitespace in strings`() {
        val jsonString = """
            {
                "id": "  123  ",
                "name": "  App Name  ",
                "category": "  Игры  ",
                "iconUrl": "  url  ",
                "description": "  desc  "
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertEquals("  123  ", result.id)
        assertEquals("  App Name  ", result.name)
        assertEquals("  Игры  ", result.category)
    }

    @Test
    fun `parses with numeric strings for numeric fields`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": "12",
                "size": "50.5",
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertEquals(12, result.ageRating)
        assertEquals(50.5f, result.size)
    }

    @Test
    fun `parses with negative numbers`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": -1,
                "size": -10.5,
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertEquals(-1, result.ageRating)
        assertEquals(-10.5f, result.size)
    }

    @Test
    fun `parses with zero values`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 0,
                "size": 0.0,
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertEquals(0, result.ageRating)
        assertEquals(0f, result.size)
    }

    @Test
    fun `parses with very large numbers`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 999999,
                "size": 999999999.9,
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertEquals(999999, result.ageRating)
        assertEquals(999999999.9f, result.size)
    }

    @Test
    fun `parses with special characters in strings`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App <script>alert('xss')</script>",
                "category": "Игры",
                "iconUrl": "url",
                "description": "Desc with emoji 🎮 and unicode \u00A9"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertEquals("App <script>alert('xss')</script>", result.name)
        assertEquals("Desc with emoji 🎮 and unicode \u00A9", result.description)
    }

    @Test
    fun `parses with unicode category names`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "category": "Игры",
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertEquals("Игры", result.category)
    }

    @Test
    fun `parses with empty strings`() {
        val jsonString = """
            {
                "id": "",
                "name": "",
                "category": "",
                "iconUrl": "",
                "description": ""
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertEquals("", result.id)
        assertEquals("", result.name)
        assertEquals("", result.category)
        assertEquals("", result.iconUrl)
        assertEquals("", result.description)
    }

    @Test
    fun `parses with array of screenshot URLs`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 12,
                "size": 50.5,
                "iconUrl": "url",
                "screenshotUrlList": ["screen1.png", "screen2.png", "screen3.png"],
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertEquals(3, result.screenshotUrlList.size)
        assertEquals("screen1.png", result.screenshotUrlList[0])
        assertEquals("screen2.png", result.screenshotUrlList[1])
        assertEquals("screen3.png", result.screenshotUrlList[2])
    }

    @Test
    fun `parses with single item in screenshot list`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "developer": "Dev",
                "category": "Игры",
                "ageRating": 12,
                "size": 50.5,
                "iconUrl": "url",
                "screenshotUrlList": ["screen1.png"],
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppDetailsDto>(jsonString)

        assertEquals(1, result.screenshotUrlList.size)
        assertEquals("screen1.png", result.screenshotUrlList[0])
    }


    @Test
    fun `handles malformed JSON gracefully`() {
        val jsonString = """
            {
                "id": "1",
                "name": "App",
                "category": "Игры",
                "iconUrl": "url",
                "description": "desc"
            }
        """.trimIndent()

        val result = json.decodeFromString<AppCatalogDto>(jsonString)

        assertNotNull(result)
    }
}