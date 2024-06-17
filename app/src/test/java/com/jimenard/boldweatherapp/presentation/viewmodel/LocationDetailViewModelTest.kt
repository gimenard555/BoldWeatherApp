package com.jimenard.boldweatherapp.presentation.viewmodel

import app.cash.turbine.test
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import com.jimenard.boldweatherapp.domain.usecase.GetLocationDetailUseCase
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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: GetLocationDetailUseCase = mockk<GetLocationDetailUseCase>()

    private lateinit var viewModel: LocationDetailViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        viewModel = LocationDetailViewModel(useCase)
    }

    @Test
    fun `fetchLocationDetailViewModel should emit Loading and Success states when use case succeeds`() =
        runTest {
            // Given
            val someWord = "col"
            val detail: LocationDetail = mockk()
            val result = Result.success(detail)

            coEvery { useCase.execute(someWord) }.returns(MutableStateFlow(result))

            // When
            val job = launch(testDispatcher) { viewModel.fetchLocationDetailViewModel(someWord) }

            // Then
            viewModel.uiState.test {
                Assert.assertEquals(UiState.Success(detail), awaitItem())
            }
            coVerify {
                useCase.execute(someWord)
            }
            job.cancel()
        }

    @Test
    fun `fetchLocationDetailViewModel should emit Loading and Error states when use case fails`() =
        runTest {
            // Given
            val someWord = "col"
            val error = Throwable("Unknown error")
            val result = Result.failure<LocationDetail>(error)

            coEvery { useCase.execute(someWord) }.returns(MutableStateFlow(result))

            // When
            val job = launch(testDispatcher) { viewModel.fetchLocationDetailViewModel(someWord) }

            // Then
            viewModel.uiState.test {
                Assert.assertEquals(UiState.Error(error.message.orEmpty()), awaitItem())
            }
            coVerify {
                useCase.execute(someWord)
            }
            job.cancel()
        }
}
