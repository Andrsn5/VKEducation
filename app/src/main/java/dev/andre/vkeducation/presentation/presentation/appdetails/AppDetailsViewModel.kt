package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import dev.andre.vkeducation.presentation.data.api.AppDetailsApi
import dev.andre.vkeducation.presentation.data.mapper.AppDetailsMapper
import dev.andre.vkeducation.presentation.data.impl.AppDetailsRepositoryImpl
import dev.andre.vkeducation.presentation.data.mapper.CategoryMapper
import dev.andre.vkeducation.presentation.domain.repository.AppDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppDetailsViewModel (
    private val appDetailsRepository: AppDetailsRepository = AppDetailsRepositoryImpl(
        api = AppDetailsApi(),
        mapper = AppDetailsMapper(CategoryMapper())
    )
): ViewModel() {


    private val _state : MutableStateFlow<AppDetailsState> = MutableStateFlow(AppDetailsState.Loading)
    val state : StateFlow<AppDetailsState> = _state.asStateFlow()


    fun loadAppDetails(id: String) {
        viewModelScope.launch {
            _state.update { AppDetailsState.Loading }

            runCatching {
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