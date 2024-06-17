package com.jimenard.boldweatherapp.domain.usecase

import com.jimenard.boldweatherapp.data.repository.WeatherRepository
import com.jimenard.boldweatherapp.domain.model.LocationDetail
import kotlinx.coroutines.flow.Flow

class GetLocationDetailUseCase(
    private val repository: WeatherRepository,
) {
    suspend fun execute(location: String): Flow<Result<LocationDetail>> = repository.getLocationDetail(location)
}
