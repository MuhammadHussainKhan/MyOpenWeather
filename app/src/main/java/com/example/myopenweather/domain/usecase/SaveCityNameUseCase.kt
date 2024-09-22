package com.example.myopenweather.domain.usecase

import com.example.myopenweather.data.pref.PreferencesManager
import javax.inject.Inject

class SaveCityNameUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    fun execute(cityName: String) {
        preferencesManager.saveCityName(cityName)
    }
}