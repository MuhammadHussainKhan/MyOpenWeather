package com.example.myopenweather.data.pref

interface PreferencesManager {
    fun saveCityName(cityName: String)
    fun getCityName(): String?
}