package com.jimenard.boldweatherapp.domain.usecase

import com.jimenard.boldweatherapp.data.repository.WeatherRepository
import com.jimenard.boldweatherapp.domain.model.Location
import kotlinx.coroutines.flow.Flow

class SearchWeatherUseCase(
    private val repository: WeatherRepository,
) {
    suspend fun execute(wordToSearch: String): Flow<Result<List<Location>>> = repository.searchLocation(wordToSearch)
}
