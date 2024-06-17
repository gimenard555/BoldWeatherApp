package com.jimenard.boldweatherapp.data.datasource

import com.jimenard.boldweatherapp.data.dataSource.RemoteDataSourceImpl
import com.jimenard.boldweatherapp.data.remote.api.WeatherApiService
import com.jimenard.boldweatherapp.data.remote.dto.LocationDetailDto
import com.jimenard.boldweatherapp.data.remote.dto.LocationDto
import com.jimenard.boldweatherapp.utils.EMPTY_STRING
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteDataSourceImplTest {
    private val mockApiService: WeatherApiService = mockk<WeatherApiService>()

    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        remoteDataSource = RemoteDataSourceImpl(mockApiService)
    }

    @Test
    fun `searchLocation() should return list of locations`() =
        runTest {
            // Given
            val wordToSearch = "New York"
            val mockApiResponse =
                listOf(
                    LocationDto(1L, "New York", "USA", "North America"),
                    LocationDto(2L, "New York Mills", "USA", "Minnesota"),
                )
            coEvery {
                mockApiService.searchLocation(
                    apiKey = EMPTY_STRING,
                    wordToSearch = wordToSearch,
                )
            } returns mockApiResponse

            // When
            val result = remoteDataSource.searchLocation(wordToSearch)

            // Then
            result.collect {
                if (it.isSuccess) {
                    assert(it.getOrDefault(emptyList()).isNotEmpty())
                }
            }
        }

    @Test
    fun `searchLocation() should return error`() =
        runTest {
            // Given
            val wordToSearch = "New York"
            val throwable: Throwable = mockk()
            coEvery {
                mockApiService.searchLocation(
                    apiKey = EMPTY_STRING,
                    wordToSearch = wordToSearch,
                )
            } throws throwable

            // When
            val result = remoteDataSource.searchLocation(wordToSearch)

            // Then
            result.catch {
                assertEquals(it, null)
            }
        }

    @Test
    fun `getLocationData() should return Location requested detail`() =
        runTest {
            // Given
            val wordToSearch = "New York"
            val mockApiResponse: LocationDetailDto = mockk()

            coEvery {
                mockApiService.getLocationData(
                    apiKey = EMPTY_STRING,
                    days = 3,
                    name = wordToSearch,
                )
            } returns mockApiResponse

            // When
            val result = remoteDataSource.getLocationData(wordToSearch)

            // Then
            result.collect {
                if (it.isSuccess) {
                    assert(it.getOrNull() != null)
                }
            }
        }

    @Test
    fun `getLocationData() should return Error`() =
        runTest {
            // Given
            val wordToSearch = "New York"
            val throwable: Throwable = mockk()

            coEvery {
                mockApiService.getLocationData(
                    apiKey = EMPTY_STRING,
                    days = 3,
                    name = wordToSearch,
                )
            } throws throwable

            // When
            val result = remoteDataSource.getLocationData(wordToSearch)

            // Then
            result.catch {
                assertEquals(it, null)
            }
        }
}
