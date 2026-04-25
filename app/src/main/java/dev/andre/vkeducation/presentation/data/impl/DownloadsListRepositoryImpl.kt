package dev.andre.vkeducation.presentation.data.impl

import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.local.downloads.DownloadsListDao
import dev.andre.vkeducation.presentation.domain.repository.DownloadsListRepository
import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadsListRepositoryImpl @Inject constructor(
    private val api: AppDetailsApi,
    private val dao: DownloadsListDao,
    private val dispatchers: CoroutineDispatchers,
) : DownloadsListRepository {
    override suspend fun toggle(id: String) {
        withContext(dispatchers.io()){
            dao.toggle(id)
        }
    }
    override fun getApk(id: String): Flow<DownloadStatus> {
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
    }.flowOn(dispatchers.io())
}