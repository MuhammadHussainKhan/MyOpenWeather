package com.example.myopenweather.data.repository

import com.example.myopenweather.data.pref.PreferencesManager

class FakePreferencesManager : PreferencesManager {

    private val fakeStorage = mutableMapOf<String, String?>()

    override fun saveCityName(cityName: String) {
        fakeStorage["city_name"] = cityName
    }

    override fun getCityName(): String? {
        return fakeStorage["city_name"]
    }

    // You can add more methods if needed for testing other preferences
}