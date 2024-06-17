package com.jimenard.boldweatherapp.data.remote.dto

data class LocationDetailDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: Forecast,
)

data class Forecast(
    val forecastday: List<ForecastDayDto>,
)

data class CurrentDto(
    val last_updated: String,
    val condition: ConditionDto,
    val maxtemp_c: Float,
    val mintemp_c: Float,
)
