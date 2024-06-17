package com.jimenard.boldweatherapp.data.remote.api

import com.jimenard.boldweatherapp.data.remote.dto.LocationDetailDto
import com.jimenard.boldweatherapp.data.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") apiKey: String,
        @Query("q") wordToSearch: String,
    ): List<LocationDto>

    @GET("forecast.json")
    suspend fun getLocationData(
        @Query("key") apiKey: String,
        @Query("days") days: Int,
        @Query("q") name: String,
    ): LocationDetailDto
}
