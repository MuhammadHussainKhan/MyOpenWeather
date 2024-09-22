package com.example.myopenweather.data.pref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class PreferencesManagerImp @Inject constructor(
    context: Context
) : PreferencesManager{
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "weather_app_prefs"
        private const val KEY_CITY_NAME = "key_city_name"
    }

    override fun saveCityName(cityName: String) {
        sharedPreferences.edit().putString(KEY_CITY_NAME, cityName).apply()
    }

    override fun getCityName(): String? {
        return sharedPreferences.getString(KEY_CITY_NAME, null)
    }
}