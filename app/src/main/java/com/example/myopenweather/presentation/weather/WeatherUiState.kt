package com.example.myopenweather.presentation.weather

import com.example.myopenweather.domain.model.Weather

data class WeatherUiState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val cityName: String = "",
)
