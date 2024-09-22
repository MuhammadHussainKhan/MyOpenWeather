package com.example.myopenweather.data.ui

import android.content.Context
import app.cash.turbine.test
import com.example.myopenweather.data.pref.PreferencesManager
import com.example.myopenweather.data.repository.FakePreferencesManager
import com.example.myopenweather.data.repository.FakeWeatherRepository
import com.example.myopenweather.domain.repository.WeatherRepository
import com.example.myopenweather.data.repository.fakeWeather
import com.example.myopenweather.domain.usecase.GetCityNameUseCase
import com.example.myopenweather.domain.usecase.GetWeatherUseCase
import com.example.myopenweather.domain.usecase.SaveCityNameUseCase
import com.example.myopenweather.presentation.weather.WeatherUiState
import com.example.myopenweather.presentation.weather.WeatherViewModel
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WeatherViewModel

    private val fakeWeatherRepository: WeatherRepository = FakeWeatherRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var getCityNameUseCase: GetCityNameUseCase
    private lateinit var saveCityNameUseCase: SaveCityNameUseCase
    private lateinit var fakePreferencesManager: FakePreferencesManager
    @Before
    fun setUp() {
        fakePreferencesManager = FakePreferencesManager()
        getWeatherUseCase = GetWeatherUseCase(fakeWeatherRepository)
        getCityNameUseCase = GetCityNameUseCase(fakePreferencesManager)  // Use the fake in your use case
        saveCityNameUseCase = SaveCityNameUseCase(fakePreferencesManager)

        viewModel = WeatherViewModel(getWeatherUseCase, saveCityNameUseCase, getCityNameUseCase)
    }

    @Test
    fun `when getWeather completes, it should emit success state`() = runTest {
        viewModel.uiState.test {

            assertEquals(WeatherUiState(weather = fakeWeather), awaitItem())
        }
    }

    @Test
    fun `when getWeather completes, it should emit success state with humidity of 60`() = runTest {
        viewModel.uiState.test {

            assertEquals(WeatherUiState(weather = fakeWeather), awaitItem())
            assertEquals(WeatherUiState(weather = fakeWeather).weather?.humidity, 60)
        }
    }
}