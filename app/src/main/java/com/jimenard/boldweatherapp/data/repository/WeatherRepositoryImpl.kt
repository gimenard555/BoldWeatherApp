package com.jimenard.boldweatherapp.data.repository

import com.jimenard.boldweatherapp.data.dataSource.RemoteDataSource
import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : WeatherRepository {
    override suspend fun searchLocation(wordToSearch: String): Flow<Result<List<Location>>> = remoteDataSource.searchLocation(wordToSearch)

    override suspend fun getLocationDetail(placeToGet: String): Flow<Result<LocationDetail>> = remoteDataSource.getLocationData(placeToGet)
}
