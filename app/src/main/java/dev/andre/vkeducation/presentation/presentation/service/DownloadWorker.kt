package dev.andre.vkeducation.presentation.presentation.service

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.andre.vkeducation.presentation.domain.repository.DownloadsListRepository
import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: DownloadsListRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_APP_ID = "app_id"
        const val KEY_PROGRESS = "progress"
        const val CHANNEL_ID = "download_channel"
        const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result {
        val id = inputData.getString(KEY_APP_ID) ?: return Result.failure()

        setForeground(createForegroundInfo(0))

        return try {
            repository.getApk(id).collect { status: DownloadStatus ->
                when (status) {
                    is DownloadStatus.Downloading -> {
                        setProgress(workDataOf(KEY_PROGRESS to status.progress.toInt()))
                        setForeground(createForegroundInfo(status.progress.toInt()))
                    }
                    is DownloadStatus.Installed -> {
                        repository.toggle(id)
                    }
                    else -> {}
                }
            }
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }

    private fun createForegroundInfo(progress: Int): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle("Скачивание")
            .setProgress(100, progress, progress == 0)
            .setOngoing(true)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return createForegroundInfo(0)
    }
}

