package com.jimenard.boldweatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.usecase.SearchWeatherUseCase
import com.jimenard.boldweatherapp.presentation.ui.components.UiState
import com.jimenard.boldweatherapp.presentation.ui.components.UiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val useCase: SearchWeatherUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState<List<Location>>>(FirstState)
        val uiState: StateFlow<UiState<List<Location>>> = _uiState.asStateFlow()

        fun searchResults(location: String) {
            viewModelScope.launch {
                useCase
                    .execute(location)
                    .onStart { _uiState.value = Loading }
                    .catch { e ->
                        _uiState.value = Error(e.message ?: "Unknown error")
                    }.collect { result ->
                        if (result.isSuccess) {
                            _uiState.value = Success(result.getOrThrow())
                        } else {
                            _uiState.value = Error("Unknown error")
                        }
                    }
            }
        }
    }
