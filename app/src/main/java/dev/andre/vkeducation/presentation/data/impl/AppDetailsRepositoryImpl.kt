package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.local.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.AppDetailsEntityMapper
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: AppDetailsDao,
    private val mapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper
): AppDetailsRepository {
    override suspend fun get(id: String): Flow<App> {
       return dao.getAppDetails(id).map { entity ->
           if (entity != null){
               entityMapper.toDomain(entity)
           }
           else{
               val dto = api.getAppDetails(id)
               if (dto == null) {
                   throw IllegalStateException("Приложение не найдено")
               }
               val domain = mapper.toDomain(dto)
               val entity = entityMapper.toEntity(domain)
               withContext(Dispatchers.IO){
                   dao.insertAppDetails(entity)
               }
               domain
           }
       }
    }
}