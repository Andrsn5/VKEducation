package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.andre.vkeducation.presentation.AppRepository
import dev.andre.vkeducation.presentation.presentation.appdetails.App
import dev.andre.vkeducation.presentation.presentation.appdetails.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppCatalogViewModel: ViewModel() {

    private val _state = MutableStateFlow(AppCatalogState())
    val state: StateFlow<AppCatalogState> = _state.asStateFlow()

    init {
        loadApps()
    }

    fun handleEvent(event: AppCatalogEvent) {
        when (event) {
            is AppCatalogEvent.OnRefresh -> {
                loadApps()
            }

            is AppCatalogEvent.OnCategoryClick -> {
                // здесь предполагается клик на иконку меню в топ баре, но я пока не знаю , что с ней делать:()
            }
        }
    }

    private fun loadApps() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(1000)

            val apps = AppRepository.getApps()

            _state.update {
                it.copy(
                    apps = apps,
                    isLoading = false
                )
            }
        }
    }
}

data class AppCatalogState(
    val apps: List<App> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class AppCatalogEvent {
    object OnRefresh : AppCatalogEvent()
    data class OnCategoryClick(val category: Category) : AppCatalogEvent()
}