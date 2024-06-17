package com.jimenard.boldweatherapp.data.repository

import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun searchLocation(wordToSearch: String): Flow<Result<List<Location>>>

    suspend fun getLocationDetail(placeToGet: String): Flow<Result<LocationDetail>>
}
