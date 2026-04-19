package dev.andre.vkeducation.domain.usecase

import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import dev.andre.vkeducation.presentation.domain.usecase.GetFilteredCatalogUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFilteredCatalogUseCaseTest {

    private val repository: AppCatalogRepository = mock()

    private lateinit var useCase: GetFilteredCatalogUseCase

    @Before
    fun setup() {
        useCase = GetFilteredCatalogUseCase(repository)
    }

    @Test
    fun `returns all when no filters`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.GAMES, "", "", false)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params()
        ).first()

        assertEquals(1, result.size)
    }

    @Test
    fun `filters by category`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.GAMES, "", "", false),
            AppCatalog("2", "App2", Category.BOOKS_AND_REFERENCE, "", "", false)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                category = setOf(Category.GAMES)
            )
        ).first()

        assertEquals(1, result.size)
    }

    @Test
    fun `filters by wishlist`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.GAMES, "", "", true),
            AppCatalog("2", "App2", Category.GAMES, "", "", false)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                onlyWishList = true
            )
        ).first()

        assertTrue(result.all { it.isInWishList })
    }

    @Test
    fun `filters by category and wishlist`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.GAMES, "", "", true),
            AppCatalog("2", "App2", Category.GAMES, "", "", false),
            AppCatalog("3", "App3", Category.BOOKS_AND_REFERENCE, "", "", true)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                category = setOf(Category.GAMES),
                onlyWishList = true
            )
        ).first()

        assertEquals(1, result.size)
        assertTrue(result.first().category == Category.GAMES)
        assertTrue(result.first().isInWishList)
    }

    @Test
    fun `returns empty list when nothing matches filters`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.GAMES, "", "", false),
            AppCatalog("2", "App2", Category.BOOKS_AND_REFERENCE, "", "", false)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                category = setOf(Category.PHOTO_AND_VIDEO),
                onlyWishList = true
            )
        ).first()

        assertTrue(result.isEmpty())
    }
}