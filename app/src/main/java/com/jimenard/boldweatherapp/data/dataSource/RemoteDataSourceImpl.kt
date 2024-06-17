package com.jimenard.boldweatherapp.data.dataSource

import com.jimenard.boldweatherapp.BuildConfig
import com.jimenard.boldweatherapp.data.mapper.toDomainModel
import com.jimenard.boldweatherapp.data.remote.api.WeatherApiService
import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl(
    private val apiService: WeatherApiService,
) : RemoteDataSource {
    override suspend fun searchLocation(wordToSearch: String): Flow<Result<List<Location>>> =
        flow {
            val response =
                apiService.searchLocation(apiKey = BuildConfig.API_KEY, wordToSearch = wordToSearch)
            emit(Result.success(response.map { it.toDomainModel() }))
        }.catch { e ->
            emit(Result.failure(e))
        }

    override suspend fun getLocationData(placeToGet: String): Flow<Result<LocationDetail>> =
        flow {
            val response =
                apiService.getLocationData(
                    apiKey = BuildConfig.API_KEY,
                    days = 3,
                    name = placeToGet,
                )
            emit(Result.success(response.toDomainModel()))
        }.catch { e ->
            emit(Result.failure(e))
        }
}
