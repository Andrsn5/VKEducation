package dev.andre.vkeducation.data.impl

import android.content.SharedPreferences
import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.AppCatalogApi
import dev.andre.vkeducation.presentation.data.dto.AppCatalogDto
import dev.andre.vkeducation.presentation.data.impl.AppCatalogRepositoryImpl
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntity
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogWithWish
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListEntity
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AppCatalogRepositoryImplTest {

    private val api: AppCatalogApi = mock()
    private val dao: AppCatalogDao = mock()
    private val mapper: AppCatalogMapper = mock()
    private val entityMapper: AppCatalogEntityMapper = mock()
    private val sharedPref: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()
    private val wishlistDao: WishListDao = mock()

    class TestDispatchers : CoroutineDispatchers {
        override fun io() = Dispatchers.Unconfined
        override fun main() = Dispatchers.Unconfined
        override fun default() = Dispatchers.Unconfined
        override fun unconfined() = Dispatchers.Unconfined
    }

    private val dispatchers = TestDispatchers()

    private lateinit var repository: AppCatalogRepositoryImpl
    
    @Before
    fun setup(){
        repository = AppCatalogRepositoryImpl(
            api,
            dao,
            mapper,
            entityMapper,
            sharedPref,
            dispatchers,
            wishlistDao
        )
    }

    @Test
    fun `getAll does NOT call api if cache is valid`() = runTest {
        whenever(sharedPref.getLong(any(), any())).thenReturn(System.currentTimeMillis())

        repository.getAll()

        verify(api, never()).getCatalog()
    }

    @Test
    fun `getAll calls api when cache expired`() = runTest {
        whenever(sharedPref.edit()).thenReturn(editor)
        whenever(sharedPref.getLong(any(), any())).thenReturn(0)
        whenever(api.getCatalog()).thenReturn(emptyList())

        repository.getAll()

        verify(api).getCatalog()
    }

    @Test
    fun `getAll saves data to db`() = runTest {
        val dto = mock<AppCatalogDto>()
        val domain = mock<AppCatalog>()
        val entity = mock<AppCatalogEntity>()
        val editor = mock<SharedPreferences.Editor>()

        whenever(sharedPref.getLong(any(), any())).thenReturn(0L)
        whenever(sharedPref.edit()).thenReturn(editor)
        whenever(editor.putLong(any(), any())).thenReturn(editor)
        whenever(api.getCatalog()).thenReturn(listOf(dto))
        whenever(mapper.toDomain(dto)).thenReturn(domain)
        whenever(entityMapper.toEntity(domain)).thenReturn(entity)

        repository.getAll()

        verify(dao).insertAppCatalog(listOf(entity))
    }

    @Test(expected = IllegalStateException::class)
    fun `getAll throws exception when api returns null`() = runTest {
        whenever(sharedPref.getLong(any(), any())).thenReturn(0)
        whenever(api.getCatalog()).thenReturn(null)

        repository.getAll()
    }

    @Test
    fun `observeAppCatalog maps wishlist`() = runTest {

        val entity = AppCatalogEntity(
            id = "1",
            name = "App",
            category = Category.ИГРЫ,
            iconUrl = "",
            description = ""
        )

        val wishListEntity = WishListEntity(
            id = "1"
        )

        val item = AppCatalogWithWish(
            app = entity,
            wishList = listOf(wishListEntity)
        )

        whenever(dao.observeAppCatalogWithWishList())
            .thenReturn(flowOf(listOf(item)))

        whenever(entityMapper.toDomain(entity))
            .thenReturn(
                AppCatalog(
                    id = "1",
                    name = "App",
                    category = Category.ИГРЫ,
                    iconUrl = "",
                    description = "",
                    isInWishList = false
                )
            )

        val result = repository.observeAppCatalog().first()

        assertTrue(result.first().isInWishList)
    }

}