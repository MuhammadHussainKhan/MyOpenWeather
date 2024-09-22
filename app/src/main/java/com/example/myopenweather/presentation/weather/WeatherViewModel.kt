package com.example.myopenweather.presentation.weather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myopenweather.domain.repository.WeatherRepository
import com.example.myopenweather.domain.usecase.GetCityNameUseCase
import com.example.myopenweather.domain.usecase.GetWeatherUseCase
import com.example.myopenweather.domain.usecase.SaveCityNameUseCase
import com.example.myopenweather.domain.utils.DEFAULT_WEATHER_DESTINATION
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.myopenweather.domain.utils.Result

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveCityNameUseCase: SaveCityNameUseCase,
    private val getCityNameUseCase: GetCityNameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState(isLoading = true))
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun saveLastCityName(userName: String) {
        saveCityNameUseCase.execute(userName)
    }

    fun loadLastCityName() {
        _uiState.value =  uiState.value.copy(cityName = getCityNameUseCase.execute()?: DEFAULT_WEATHER_DESTINATION)
    }
// var state by mutableStateOf(MerchantUiState())



    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    init {
        loadLastCityName()
        getWeather(uiState.value.cityName)
    }

    fun getWeather(city: String = DEFAULT_WEATHER_DESTINATION) {

        saveLastCityName(city)

        getWeatherUseCase.execute(city).map { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.value = WeatherUiState(weather = result.data)
                }

                is Result.Error -> {
                    _uiState.value = WeatherUiState(errorMessage = result.errorMessage)
                }

                Result.Loading -> {
                    _uiState.value = WeatherUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}