package com.example.myopenweather.data.network

import com.example.myopenweather.BuildConfig
import com.example.myopenweather.data.model.ForecastResponse
import com.example.myopenweather.domain.utils.DEFAULT_WEATHER_DESTINATION
import com.example.myopenweather.domain.utils.NUMBER_OF_DAYS

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("q") city: String = DEFAULT_WEATHER_DESTINATION,
        @Query("days") days: Int = NUMBER_OF_DAYS,
    ): ForecastResponse
}