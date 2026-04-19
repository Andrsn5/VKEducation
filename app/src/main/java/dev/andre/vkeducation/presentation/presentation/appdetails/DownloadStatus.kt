package dev.andre.vkeducation.presentation.presentation.appdetails

sealed interface DownloadStatus {
    data object Prepare: DownloadStatus
    data object Started: DownloadStatus
    data class Downloading(val progress: Long): DownloadStatus
    data object Installed: DownloadStatus
    data object Error: DownloadStatus
}