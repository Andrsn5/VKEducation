package dev.andre.vkeducation.presentation.domain.repository

import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus
import kotlinx.coroutines.flow.Flow

interface DownloadsListRepository {
    suspend fun toggle(id: String)
    fun getApk(id: String): Flow<DownloadStatus>
    suspend fun startDownload(id: String)
    suspend fun cancelDownload(id: String)
    fun observeDownloadStatus(id: String): Flow<DownloadStatus>
}