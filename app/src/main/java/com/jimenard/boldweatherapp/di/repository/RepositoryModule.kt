package com.jimenard.boldweatherapp.di.repository

import com.jimenard.boldweatherapp.data.dataSource.RemoteDataSource
import com.jimenard.boldweatherapp.data.repository.WeatherRepository
import com.jimenard.boldweatherapp.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesRepository(dataSource: RemoteDataSource): WeatherRepository = WeatherRepositoryImpl(dataSource)
}
