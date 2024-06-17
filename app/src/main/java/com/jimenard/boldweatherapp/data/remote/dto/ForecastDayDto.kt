package com.jimenard.boldweatherapp.data.remote.dto

data class ForecastDayDto(
    val day: DayDto,
    val date: String,
)

data class DayDto(
    val condition: ConditionDto,
    val maxtemp_c: Float,
    val mintemp_c: Float,
)
