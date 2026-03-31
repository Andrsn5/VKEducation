package dev.andre.vkeducation.presentation.presentation.appdetails

import dev.andre.vkeducation.presentation.domain.App

sealed interface AppDetailsState{
    data object Loading: AppDetailsState
    data object Error: AppDetailsState
    data class Content(val app: App?): AppDetailsState
}