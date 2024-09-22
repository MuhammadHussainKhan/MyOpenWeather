package com.example.myopenweather.domain.model


data class Forecast(
    val date: String,
    val maxTemp: String,
    val minTemp: String,
    val sunrise: String,
    val sunset: String,
    val icon: String,
    val hour: List<Hour>,
)
