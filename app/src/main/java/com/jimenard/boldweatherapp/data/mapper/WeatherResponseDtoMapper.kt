package com.jimenard.boldweatherapp.data.mapper

import com.jimenard.boldweatherapp.data.remote.dto.ConditionDto
import com.jimenard.boldweatherapp.data.remote.dto.ForecastDayDto
import com.jimenard.boldweatherapp.data.remote.dto.LocationDetailDto
import com.jimenard.boldweatherapp.data.remote.dto.LocationDto
import com.jimenard.boldweatherapp.domain.model.Condition
import com.jimenard.boldweatherapp.domain.model.Day
import com.jimenard.boldweatherapp.domain.model.Location
import com.jimenard.boldweatherapp.domain.model.LocationDetail

fun LocationDto.toDomainModel(): Location = Location(name = this.name, country = this.country, region = this.region)

fun LocationDetailDto.toDomainModel() =
    LocationDetail(
        location = this.location.toDomainModel(),
        days = this.forecast.forecastday.map { it.toDomainModel() },
    )

fun ConditionDto.toDomainModel() =
    Condition(
        text = this.text,
        icon = "https:${this.icon}",
    )

fun ForecastDayDto.toDomainModel() =
    Day(
        date = this.date,
        condition = this.day.condition.toDomainModel(),
        tempAverage = (this.day.maxtemp_c + this.day.mintemp_c) / 2,
    )
