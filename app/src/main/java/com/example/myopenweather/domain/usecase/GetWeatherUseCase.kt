package com.example.myopenweather.domain.usecase

import com.example.myopenweather.domain.model.Weather
import com.example.myopenweather.domain.repository.WeatherRepository
import com.example.myopenweather.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase@Inject constructor(
    private val repository: WeatherRepository) {

      fun execute(city: String): Flow<Result<Weather>> = repository.getWeatherForecast(city)
}
