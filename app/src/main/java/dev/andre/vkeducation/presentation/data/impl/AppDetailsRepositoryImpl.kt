package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntityMapper
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val api: AppDetailsApi,
    private val dao: AppDetailsDao,
    private val mapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper,
    private val dispatchers: CoroutineDispatchers,
): AppDetailsRepository {
    override suspend fun get(id: String) = withContext(dispatchers.io()){
        val dto = api.getAppDetails(id)
        if (dto == null) {
            throw IllegalStateException("Приложение не найдено")
        }
        val domain = mapper.toDomain(dto)

        val entity = entityMapper.toEntity(domain)

        dao.insertAppDetails(entity)
    }

    override fun observeAppDetails(id: String): Flow<App> {
        return combine(
            dao.observeDetailsWithWishlist(id).filterNotNull(),
            dao.observeDetailsWithDownloads(id).filterNotNull()
        ) { wishItem, downloadsItem ->
            entityMapper.toDomain(wishItem.app).copy(
                isInWishList = wishItem.wishList.isNotEmpty(),
                isDownload = downloadsItem.downloadsList.isNotEmpty()
            )
        }
    }
}