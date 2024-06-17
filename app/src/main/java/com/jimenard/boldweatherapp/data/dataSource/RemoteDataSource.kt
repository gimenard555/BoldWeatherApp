package com.jimenard.boldweatherapp.data.dataSource

import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun searchLocation(wordToSearch: String): Flow<Result<List<Location>>>

    suspend fun getLocationData(placeToGet: String): Flow<Result<LocationDetail>>
}
