package com.example.myopenweather.di

import com.example.myopenweather.data.network.WeatherApi
import com.example.myopenweather.data.repository.DefaultWeatherRepository
import com.example.myopenweather.data.repository.WeatherRepository
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
    fun provideRepository(weatherApi: WeatherApi): WeatherRepository =
        DefaultWeatherRepository(weatherApi)
}