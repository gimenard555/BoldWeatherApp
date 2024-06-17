package com.jimenard.boldweatherapp.presentation.viewmodel

import app.cash.turbine.test
import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.usecase.SearchWeatherUseCase
import com.jimenard.boldweatherapp.presentation.ui.components.UiState
import com.jimenard.boldweatherapp.rule.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: SearchWeatherUseCase = mockk<SearchWeatherUseCase>()

    private lateinit var viewModel: SearchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        viewModel = SearchViewModel(useCase)
    }

    @Test
    fun `searchResults should emit Loading and Success states when use case succeeds`() =
        runTest {
            // Given
            val someWord = "col"
            val locations = listOf(Location("City1", "Country1", "Region1"))
            val result = Result.success(locations)

            coEvery { useCase.execute(someWord) }.returns(MutableStateFlow(result))

            // When
            val job = launch(testDispatcher) { viewModel.searchResults(someWord) }

            // Then
            viewModel.uiState.test {
                assertEquals(UiState.Success(locations), awaitItem())
            }
            coVerify {
                useCase.execute(someWord)
            }
            job.cancel()
        }

    @Test
    fun `searchResults should emit Loading and Error states when use case fails`() =
        runTest {
            // Given
            val someWord = "col"
            val error = Throwable("Unknown error")
            val result = Result.failure<List<Location>>(error)

            coEvery { useCase.execute(someWord) }.returns(MutableStateFlow(result))

            // When
            val job = launch(testDispatcher) { viewModel.searchResults(someWord) }

            // Then
            viewModel.uiState.test {
                assertEquals(UiState.Error(error.message.orEmpty()), awaitItem())
            }
            coVerify {
                useCase.execute(someWord)
            }
            job.cancel()
        }
}
