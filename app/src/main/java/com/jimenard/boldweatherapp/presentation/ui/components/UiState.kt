package com.jimenard.boldweatherapp.presentation.ui.components

sealed class UiState<out T> {
    data object FirstState : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val message: String,
    ) : UiState<Nothing>()
}
