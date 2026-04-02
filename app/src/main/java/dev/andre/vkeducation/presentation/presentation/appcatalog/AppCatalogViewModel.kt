package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.data.api.AppCatalogApi
import dev.andre.vkeducation.presentation.data.impl.AppCatalogRepositoryImpl
import dev.andre.vkeducation.presentation.data.mapper.AppCatalogMapper
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppCatalogViewModel (
    private val repository: AppCatalogRepository = AppCatalogRepositoryImpl(
        api = AppCatalogApi(),
        mapper = AppCatalogMapper(CategoryMapper())
    )
): ViewModel() {
    private val _state: MutableStateFlow<AppCatalogState> = MutableStateFlow(AppCatalogState.Loading)
    val state: StateFlow<AppCatalogState> = _state.asStateFlow()

    private val _events = Channel<AppCatalogEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun showHelloSnackbar() {
        viewModelScope.launch {
            _events.send(AppCatalogEvent.ShowSnackbar(R.string.hello))
        }
    }

    init {
        loadApps()
    }


     fun loadApps() {
        viewModelScope.launch {
            _state.update {  AppCatalogState.Loading }
            runCatching {
                val apps = repository.getAll()

                _state.update {
                    AppCatalogState.Content(appCatalog = apps)
                }
            }.onFailure { e ->
                if (e is CancellationException) throw e
                _state.update { AppCatalogState.Error }
            }
        }
    }


}