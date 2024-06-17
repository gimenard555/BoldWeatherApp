package com.jimenard.boldweatherapp.domain.model

data class LocationDetail(
    val location: Location,
    val days: List<Day>,
)
