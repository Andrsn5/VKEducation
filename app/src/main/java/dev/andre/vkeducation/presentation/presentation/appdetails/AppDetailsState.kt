package dev.andre.vkeducation.presentation.presentation.appdetails

sealed interface AppDetailsState{
    data object Loading: AppDetailsState
    data object Error: AppDetailsState
    data class Content(val app: App?): AppDetailsState
}