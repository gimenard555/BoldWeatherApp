package com.jimenard.boldweatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import com.jimenard.boldweatherapp.domain.usecase.GetLocationDetailUseCase
import com.jimenard.boldweatherapp.presentation.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel
    @Inject
    constructor(
        private val useCase: GetLocationDetailUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState<LocationDetail>>(UiState.FirstState)
        val uiState: StateFlow<UiState<LocationDetail>> = _uiState.asStateFlow()

        fun fetchLocationDetailViewModel(location: String) {
            viewModelScope.launch {
                useCase
                    .execute(location)
                    .onStart { _uiState.value = UiState.Loading }
                    .catch { e ->
                        _uiState.value = UiState.Error(e.message ?: "Unknown error")
                    }.collect { result ->
                        if (result.isSuccess) {
                            _uiState.value = UiState.Success(result.getOrThrow())
                        } else {
                            _uiState.value = UiState.Error("Unknown error")
                        }
                    }
            }
        }
    }
