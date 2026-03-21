package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import dev.andre.vkeducation.presentation.data.AppDetailsApi
import dev.andre.vkeducation.presentation.data.AppDetailsMapper
import dev.andre.vkeducation.presentation.data.AppDetailsRepostoryImpl
import dev.andre.vkeducation.presentation.domain.AppDetailsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppDetailsViewModel(
    private val appDetailsRepository: AppDetailsRepository = AppDetailsRepostoryImpl(
        appDetailsApi = AppDetailsApi(),
        appDetailsMapper = AppDetailsMapper()
    )
): ViewModel() {


    private val _state : MutableStateFlow<AppDetailsState> = MutableStateFlow(AppDetailsState.Loading)
    val state : StateFlow<AppDetailsState> = _state.asStateFlow()


    fun loadAppDetails(id: String) {
        viewModelScope.launch {
            _state.update { AppDetailsState.Loading }

            runCatching {
                delay(2000)

                val app = appDetailsRepository.get(id)

                _state.update {
                    AppDetailsState.Content(
                        app = app
                    )
                }
            }.onFailure { e->
                if (e is CancellationException) throw e
                _state.update { AppDetailsState.Error }
            }
        }
    }

}