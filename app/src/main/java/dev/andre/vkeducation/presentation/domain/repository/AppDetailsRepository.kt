package dev.andre.vkeducation.presentation.domain.repository

import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus
import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {
    suspend fun get(id: String)

    suspend fun toggleWishList(id: String)

    fun observeAppDetails(id: String): Flow<App>

    suspend fun getApk(id: String) : Flow<DownloadStatus>
}