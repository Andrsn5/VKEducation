package dev.andre.vkeducation.presentation.data.impl

import android.content.SharedPreferences
import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import androidx.core.content.edit
import dev.andre.vkeducation.presentation.data.api.AppCatalogApi
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class AppCatalogRepositoryImpl @Inject constructor (
    private val api: AppCatalogApi,
    private val dao: AppCatalogDao,
    private val mapper: AppCatalogMapper,
    private val appCatalogMapper: AppCatalogEntityMapper,
    private val sharedPref: SharedPreferences,
    private val dispatchers: CoroutineDispatchers,
    private val wishlistDao: WishListDao
): AppCatalogRepository {
    companion object{
        private const val CACHE_DURATION_MS = 1000L
        private const val KEY_LAST_UPDATED = "last_updated"
    }

    override suspend fun getAll(){
        val lastUpdate = sharedPref.getLong(KEY_LAST_UPDATED,0)
        val now = System.currentTimeMillis()
        if (now - lastUpdate < CACHE_DURATION_MS){
             return
        }

        val apiResponse = api.getCatalog()


        if (apiResponse == null){
            throw IllegalStateException("Приложения не найдены")
        }

        val domain = apiResponse.map { mapper.toDomain(it) }
        val entity = domain.map { appCatalogMapper.toEntity(it) }


        withContext(dispatchers.io()){
            dao.insertAppCatalog(entity)
            sharedPref.edit { putLong(KEY_LAST_UPDATED, now) }
        }
    }

    override suspend fun observeAppCatalog(): Flow<List<AppCatalog>> {
        return dao.observeAppCatalogWithWishList().map { list ->
            list.map { item ->
                appCatalogMapper.toDomain(item.app).copy(
                    isInWishList = item.isInWishList
                )
            }
        }
    }

    override suspend fun toggleWishList(id: String) {
        wishlistDao.toggle(id)
    }
}