package com.jimenard.boldweatherapp.domain.model

data class Day(
    val date: String,
    val condition: Condition,
    val tempAverage: Float,
)
