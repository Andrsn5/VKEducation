package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntityMapper
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val api: AppDetailsApi,
    private val dao: AppDetailsDao,
    private val mapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper,
    private val dispatchers: CoroutineDispatchers,
    private val wishlistDao: WishListDao
): AppDetailsRepository {
    override suspend fun get(id: String) {
        val dto = api.getAppDetails(id)
        if (dto == null) {
            throw IllegalStateException("Приложение не найдено")
        }
        val domain = mapper.toDomain(dto)

        val entity = entityMapper.toEntity(domain)
        withContext(dispatchers.io()){
            dao.insertAppDetails(entity)
        }
    }

    override suspend fun toggleWishList(id: String) {
        wishlistDao.toggle(id)
    }

    override fun observeAppDetails(id: String): Flow<App> {
        return dao.observeDetailsWithWishlist(id).filterNotNull().map { item ->
            entityMapper.toDomain(item.app).copy(
                isInWishList = item.isInWishList
            )
        }
    }

    override suspend fun getApk(id: String): Flow<DownloadStatus> {
        return flow {
            emit(DownloadStatus.Prepare)

            api.getApk(id)
            emit(DownloadStatus.Started)

            emitAll(
                processeApk().map {
                    DownloadStatus.Downloading(it)
                }
                )

            emit(DownloadStatus.Installed)
        }.catch {
            emit(DownloadStatus.Error)
        }
    }

    private fun processeApk(): Flow<Long> = flow {
        for (i in 1 .. 100){
            emit(i.toLong())
            delay(100L)
        }
    }
}