package dev.andre.vkeducation.presentation.presentation.appcatalog

import dev.andre.vkeducation.presentation.domain.AppCatalog

sealed interface AppCatalogState{
    data object Loading: AppCatalogState
    data object Error: AppCatalogState
    data class Content(
        val appCatalog: List<AppCatalog>,
    ) : AppCatalogState
}
