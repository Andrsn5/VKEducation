package dev.andre.vkeducation.presentation.data.impl

import android.content.SharedPreferences
import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class AppCatalogRepositoryImpl @Inject constructor (
    private val api: ApiService,
    private val dao: AppCatalogDao,
    private val mapper: AppCatalogMapper,
    private val appCatalogMapper: AppCatalogEntityMapper,
    private val sharedPref: SharedPreferences
): AppCatalogRepository {
    companion object{
        private const val CACHE_DURATION_MS = 1000L
        private const val KEY_LAST_UPDATED = "last_updated"
    }

    override suspend fun getAll(): Flow<List<AppCatalog>> {
        return flow {
            val lastUpdate = sharedPref.getLong(KEY_LAST_UPDATED,0)
            val now = System.currentTimeMillis()
            if (now - lastUpdate < CACHE_DURATION_MS){
                val cachedApps = dao.getAll().firstOrNull()
                if (!cachedApps.isNullOrEmpty()){
                    emit(cachedApps.map { appCatalogMapper.toDomain(it)})
                    return@flow
                }
            }

            val apiResponse = try {
                api.getCatalog()
            } catch (e: Exception){
                val fallbackApps = dao.getAll().firstOrNull()
                if (!fallbackApps.isNullOrEmpty()){
                    emit(fallbackApps.map { appCatalogMapper.toDomain(it)})
                    return@flow
                }
                throw IllegalStateException("Приложения не найдены")
            }

            if (apiResponse == null){
                throw IllegalStateException("Приложения не найдены")
            }

            val domain = apiResponse.map { mapper.toDomain(it) }
            val entity = domain.map { appCatalogMapper.toEntity(it) }

            withContext(Dispatchers.IO){
                dao.insertAppCatalog(entity)
                sharedPref.edit().putLong(KEY_LAST_UPDATED, now).apply()
            }
            emit(domain)
        }
    }
}