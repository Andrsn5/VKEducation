package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.domain.repository.AppCatalogRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppCatalogViewModel @Inject constructor(
    private val repository: AppCatalogRepository,
    private val savedStateHandle: SavedStateHandle
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

    private companion object{
        private const val KEY_APP_CATALOG = "app_catalog"
    }

    val scrollIndex : StateFlow<Int> = savedStateHandle.getStateFlow(KEY_APP_CATALOG, 0).also {
        Timber.d("ScrollIndex initialized = ${it.value}")
    }

    fun updateScrollIndex(index: Int){
        Timber.d("Saving scroll index = $index")
        savedStateHandle.set(KEY_APP_CATALOG, index)
    }

    init {
        loadApps()
    }


     fun loadApps() {
        viewModelScope.launch {
            _state.update {  AppCatalogState.Loading }

            repository.getAll().catch { e->
                if (e is CancellationException) throw e
                _state.update { AppCatalogState.Error }
                Timber.e(e)
            }.collect { appCatalogs ->
                _state.update { AppCatalogState.Content(appCatalog = appCatalogs) }
            }
        }
    }


}