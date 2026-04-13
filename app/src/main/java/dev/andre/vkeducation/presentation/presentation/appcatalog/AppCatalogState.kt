package dev.andre.vkeducation.presentation.presentation.appcatalog

import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.usecase.GetFilteredCatalogUseCase

sealed interface AppCatalogState{
    data object Loading: AppCatalogState
    data object Error: AppCatalogState
    data class Content(
        val appCatalog: List<AppCatalog>,
        val filterParams: GetFilteredCatalogUseCase.Params = GetFilteredCatalogUseCase.Params()
    ) : AppCatalogState
}
