package com.jimenard.boldweatherapp.data.repository

import com.jimenard.boldweatherapp.data.dataSource.RemoteDataSourceImpl
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
    private val dataSource: RemoteDataSourceImpl = mockk<RemoteDataSourceImpl>()

    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        repository = WeatherRepositoryImpl(dataSource)
    }

    @Test
    fun `searchLocation() should return response`() =
        runTest {
            // Given
            val wordToSearch = "New York"
            val throwable: Throwable = mockk()
            coEvery {
                dataSource.searchLocation(wordToSearch = wordToSearch)
            } throws throwable

            // When
            val result = repository.searchLocation(wordToSearch)

            // Then
            result.catch {
                Assert.assertEquals(it, null)
            }
            coVerify {
                dataSource.searchLocation(wordToSearch)
            }
        }

    @Test
    fun `getLocationDetail() should return response`() =
        runTest {
            // Given
            val wordToSearch = "New York"
            val detail: LocationDetail = mockk()
            coEvery {
                dataSource.getLocationData(wordToSearch)
            } returns flowOf(Result.success(detail))

            // When
            val result = repository.getLocationDetail(wordToSearch)

            // Then
            result.catch {
                Assert.assertEquals(it, null)
            }
            coVerify {
                dataSource.getLocationData(wordToSearch)
            }
        }
}
