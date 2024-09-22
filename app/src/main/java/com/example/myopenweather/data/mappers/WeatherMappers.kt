package com.example.myopenweather.data.mappers

import com.example.myopenweather.data.model.ForecastResponse
import com.example.myopenweather.data.model.ForecastResponse.NetworkForecastDTO.NetworkForecastdayDTO
import com.example.myopenweather.domain.model.Condition
import com.example.myopenweather.domain.model.Forecast
import com.example.myopenweather.domain.model.Hour
import com.example.myopenweather.domain.model.Weather


fun ForecastResponse.toWeather(): Weather = Weather(
    temperature = current.tempC.toInt(),
    date = forecast.forecastday[0].date,
    wind = current.windKph.toInt(),
    humidity = current.humidity,
    feelsLike = current.feelslikeC.toInt(),
    condition = current.condition.toCondition(),
    uv = current.uv.toInt(),
    name = location.name,
    forecasts = forecast.forecastday.map { networkForecastday ->
        networkForecastday.toWeatherForecast()
    }
)

fun ForecastResponse.ConditionDTO.toCondition(): Condition = Condition(
    text = text,
    code = code,
    icon = icon
)

fun NetworkForecastdayDTO.toWeatherForecast(): Forecast = Forecast(
    date = date,
    maxTemp = day.maxtempC.toInt().toString(),
    minTemp = day.mintempC.toInt().toString(),
    sunrise = astro.sunrise,
    sunset = astro.sunset,
    icon = day.condition.icon,
    hour = hour.map { networkHour ->
        networkHour.toHour()
    }
)

fun NetworkForecastdayDTO.NetworkHourDTO.toHour(): Hour = Hour(
    time = time,
    icon = condition.icon,
    temperature = tempC.toInt().toString(),
)