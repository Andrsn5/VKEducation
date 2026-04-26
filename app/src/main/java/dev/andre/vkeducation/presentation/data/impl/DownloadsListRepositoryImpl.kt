package dev.andre.vkeducation.presentation.data.impl

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import dev.andre.vkeducation.presentation.common.CoroutineDispatchers
import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.local.downloads.DownloadsListDao
import dev.andre.vkeducation.presentation.domain.repository.DownloadsListRepository
import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus
import dev.andre.vkeducation.presentation.presentation.service.DownloadWorker
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
    private val workManager: WorkManager,
    private val dispatchers: CoroutineDispatchers,
) : DownloadsListRepository {
    override suspend fun toggle(id: String) {
        withContext(dispatchers.io()){
            dao.toggle(id)
        }
    }
    override suspend fun startDownload(id: String) {
        val inputData = workDataOf(DownloadWorker.KEY_APP_ID to id)

        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(inputData)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .addTag(id)
            .build()

        workManager.enqueueUniqueWork(
            id,
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    override fun observeDownloadStatus(id: String): Flow<DownloadStatus> {
        return workManager.getWorkInfosForUniqueWorkFlow(id)
            .map { workInfos ->
                val info = workInfos.firstOrNull()
                    ?: return@map DownloadStatus.Prepare

                when (info.state) {
                    WorkInfo.State.ENQUEUED,
                    WorkInfo.State.BLOCKED    -> DownloadStatus.Prepare
                    WorkInfo.State.RUNNING    -> {
                        val progress = info.progress.getInt(DownloadWorker.KEY_PROGRESS, 0)
                        if (progress == 0) DownloadStatus.Started
                        else DownloadStatus.Downloading(progress.toLong())
                    }
                    WorkInfo.State.SUCCEEDED  -> DownloadStatus.Installed
                    WorkInfo.State.FAILED,
                    WorkInfo.State.CANCELLED  -> DownloadStatus.Error
                }
            }
    }

    override suspend fun cancelDownload(id: String) {
        workManager.cancelUniqueWork(id)
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