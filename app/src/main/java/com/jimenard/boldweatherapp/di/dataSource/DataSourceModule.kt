package com.jimenard.boldweatherapp.di.dataSource

import com.jimenard.boldweatherapp.data.dataSource.RemoteDataSource
import com.jimenard.boldweatherapp.data.dataSource.RemoteDataSourceImpl
import com.jimenard.boldweatherapp.data.remote.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun providesDataSource(weatherApi: WeatherApiService): RemoteDataSource = RemoteDataSourceImpl(weatherApi)
}
