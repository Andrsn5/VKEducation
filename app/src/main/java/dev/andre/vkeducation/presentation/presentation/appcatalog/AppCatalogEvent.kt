package dev.andre.vkeducation.presentation.presentation.appcatalog

sealed interface AppCatalogEvent {
    data class ShowSnackbar(val textResId: Int): AppCatalogEvent
}