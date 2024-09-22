package com.example.myopenweather.data.pref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


class PreferencesManager @Inject constructor(
    context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "weather_app_prefs"
        private const val KEY_CITY_NAME = "key_city_name"
    }

    fun saveUserName(userName: String) {
        sharedPreferences.edit().putString(KEY_CITY_NAME, userName).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_CITY_NAME, null)
    }
}