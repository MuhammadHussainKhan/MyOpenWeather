package com.example.myopenweather.data.repository


import com.example.myopenweather.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import com.example.myopenweather.domain.utils.Result

interface WeatherRepository {
    fun getWeatherForecast(city: String): Flow<Result<Weather>>
}