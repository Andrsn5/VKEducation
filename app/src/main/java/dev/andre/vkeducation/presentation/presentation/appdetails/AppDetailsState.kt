package dev.andre.vkeducation.presentation.presentation.appdetails

import dev.andre.vkeducation.presentation.domain.model.App
import dev.andre.vkeducation.presentation.presentation.appdetails.DownloadStatus

sealed interface AppDetailsState{
    data object Loading: AppDetailsState
    data object Error: AppDetailsState
    data class Content(
        val app: App?,
        val isInWishList: Boolean,
        val status: DownloadStatus = DownloadStatus.Prepare
        ): AppDetailsState
}