package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsEntityMapper
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: AppDetailsDao,
    private val mapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper,
    private val dispatchers: CoroutineDispatchers
): AppDetailsRepository {
    override suspend fun get(id: String) {
        val dto = api.getAppDetails(id)
        if (dto == null) {
            throw IllegalStateException("Приложение не найдено")
        }
        val domain = mapper.toDomain(dto)

        val local = dao.getAppDetails(id).firstOrNull()
        val merged = domain.copy(
            isInWishList = local?.isInWishList ?:false
        )

        val entity = entityMapper.toEntity(merged)
        withContext(dispatchers.io()){
            dao.insertAppDetails(entity)
        }
    }

    override suspend fun toggleWishList(id: String) {
        val entity = dao.getAppDetails(id).firstOrNull()
        if (entity == null) throw IllegalStateException("Приложение не найдено")
        entity.let {
            dao.updateWishListStatus(id,!it.isInWishList)
        }
    }

    override fun observeAppDetails(id: String): Flow<App> {
        return dao.getAppDetails(id).filterNotNull().map { data ->
            entityMapper.toDomain(data)
        }
    }
}