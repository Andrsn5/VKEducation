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
            AppCatalog("1", "App", Category.ИГРЫ, "", "", false)
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
            AppCatalog("1", "App", Category.ИГРЫ, "", "", false),
            AppCatalog("2", "App2", Category.КНИГИ_И_СПРАВОЧНИКИ, "", "", false)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                category = setOf(Category.ИГРЫ)
            )
        ).first()

        assertEquals(1, result.size)
    }

    @Test
    fun `filters by wishlist`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.ИГРЫ, "", "", true),
            AppCatalog("2", "App2", Category.ИГРЫ, "", "", false)
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
            AppCatalog("1", "App", Category.ИГРЫ, "", "", true),
            AppCatalog("2", "App2", Category.ИГРЫ, "", "", false),
            AppCatalog("3", "App3", Category.КНИГИ_И_СПРАВОЧНИКИ, "", "", true)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                category = setOf(Category.ИГРЫ),
                onlyWishList = true
            )
        ).first()

        assertEquals(1, result.size)
        assertTrue(result.first().category == Category.ИГРЫ)
        assertTrue(result.first().isInWishList)
    }

    @Test
    fun `returns empty list when nothing matches filters`() = runTest {
        val list = listOf(
            AppCatalog("1", "App", Category.ИГРЫ, "", "", false),
            AppCatalog("2", "App2", Category.КНИГИ_И_СПРАВОЧНИКИ, "", "", false)
        )

        whenever(repository.observeAppCatalog())
            .thenReturn(flowOf(list))

        val result = useCase(
            GetFilteredCatalogUseCase.Params(
                category = setOf(Category.ФОТО_И_ВИДЕО),
                onlyWishList = true
            )
        ).first()

        assertTrue(result.isEmpty())
    }
}