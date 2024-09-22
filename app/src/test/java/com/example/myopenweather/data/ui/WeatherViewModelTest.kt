package com.example.myopenweather.data.ui

import app.cash.turbine.test
import com.example.myopenweather.data.repository.FakeWeatherRepository
import com.example.myopenweather.domain.repository.WeatherRepository
import com.example.myopenweather.data.repository.fakeWeather
import com.example.myopenweather.presentation.weather.WeatherUiState
import com.example.myopenweather.presentation.weather.WeatherViewModel
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WeatherViewModel

    private val weatherRepository: WeatherRepository = FakeWeatherRepository

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(weatherRepository)
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