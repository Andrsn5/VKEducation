package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.data.api.ApiService
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogEntityMapper
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppCatalogRepositoryImpl @Inject constructor (
    private val api: ApiService,
    private val dao: AppCatalogDao,
    private val mapper: AppCatalogMapper,
    private val appCatalogMapper: AppCatalogEntityMapper
): AppCatalogRepository {
    override suspend fun getAll(): Flow<List<AppCatalog>> {
         return dao.getAll().map { list ->
            if (!list.isEmpty()){
                val list = list.map { appCatalogMapper.toDomain(it) }
                list
            }
            else{
                val api = api.getCatalog()
                if (api == null) throw IllegalStateException("Ошибка загрузки")
                val listDomain = api.map { mapper.toDomain(it) }
                withContext(Dispatchers.IO){
                    dao.insertAppCatalog(listDomain.map { appCatalogMapper.toEntity(it) })
                }
                listDomain
            }
        }
    }
}