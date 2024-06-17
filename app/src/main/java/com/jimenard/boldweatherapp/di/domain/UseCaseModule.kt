package com.jimenard.boldweatherapp.di.domain

import com.jimenard.boldweatherapp.data.repository.WeatherRepository
import com.jimenard.boldweatherapp.domain.usecase.GetLocationDetailUseCase
import com.jimenard.boldweatherapp.domain.usecase.SearchWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun providesSearchWeatherUseCase(repository: WeatherRepository): SearchWeatherUseCase = SearchWeatherUseCase(repository)

    @Singleton
    @Provides
    fun providesGetLocationDetailUseCase(repository: WeatherRepository): GetLocationDetailUseCase = GetLocationDetailUseCase(repository)
}
