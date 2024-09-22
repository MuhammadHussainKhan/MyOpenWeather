package com.example.myopenweather.domain.usecase

import com.example.myopenweather.data.pref.PreferencesManager
import javax.inject.Inject

class GetCityNameUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    fun execute(): String? {
        return preferencesManager.getUserName()
    }
}