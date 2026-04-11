package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor (
    private val appDetailsRepository: AppDetailsRepository
): ViewModel() {


    private val _state : MutableStateFlow<AppDetailsState> = MutableStateFlow(AppDetailsState.Loading)
    val state : StateFlow<AppDetailsState> = _state.asStateFlow()

    private var downloadingApk : Job? = null


    fun observeAppDetails(id: String){
        viewModelScope.launch {
            _state.update { AppDetailsState.Loading }

            launch {
                appDetailsRepository.observeAppDetails(id)
                    .catch { e->
                        _state.update { AppDetailsState.Error }
                    }.collect { app ->
                        _state.update { AppDetailsState.Content(
                            app = app,
                            isInWishList = app.isInWishList
                        ) }
                    }
            }

            launch {
                runCatching {
                    appDetailsRepository.get(id)
                }.onFailure { e->
                    if (e is CancellationException) throw e
                    _state.update { AppDetailsState.Error }
                }
            }

        }
    }


    fun toggleWishList(id: String){
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is AppDetailsState.Content) {
                _state.update {
                    currentState.copy(isInWishList = !currentState.isInWishList)
                }
            }
            appDetailsRepository.toggleWishList(id)
        }
    }

    fun download(id: String){
        downloadingApk?.cancel()
        downloadingApk = viewModelScope.launch {
            appDetailsRepository.getApk(id).collect { downloadState->
                _state.update { currentState->
                    if (currentState is AppDetailsState.Content){
                        currentState.copy( status = downloadState)
                    }
                    else{
                        currentState
                    }
                }
            }
        }
    }
    fun delete(id: String){
        viewModelScope.launch {
            _state.update { currentState ->
                if (currentState is AppDetailsState.Content){
                    currentState.copy(status = DownloadStatus.Prepare)
                }
                else{
                    currentState
                }
            }
        }
    }

}