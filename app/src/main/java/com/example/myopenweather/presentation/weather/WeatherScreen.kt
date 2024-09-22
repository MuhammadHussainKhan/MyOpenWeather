package com.example.myopenweather.presentation.weather

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myopenweather.R
import com.example.myopenweather.domain.model.Condition
import com.example.myopenweather.domain.model.Forecast
import com.example.myopenweather.domain.model.Hour
import com.example.myopenweather.domain.model.Weather
import com.example.myopenweather.domain.utils.DateUtil.toFormattedDate
import com.example.myopenweather.domain.utils.DateUtil.toFormattedFullSpellDay
import com.example.myopenweather.presentation.ui.theme.MyOpenWeatherTheme

import com.example.myopenweather.presentation.weather.components.Animation
import com.example.myopenweather.presentation.weather.components.ForecastComponent
import com.example.myopenweather.presentation.weather.components.HourlyComponent
import com.example.myopenweather.presentation.weather.components.WeatherComponent

import java.util.Locale
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    var searchText by remember { mutableStateOf("") }

    val uiState: WeatherUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {

                    AutocompleteCitySearchBar(
                        searchText = searchText,
                        onTextChange = { searchText=it },
                        onCloseClicked = { searchText="" },
                        onSearchClicked = {
                            searchText=""
                            viewModel.getWeather(it) },
                        )

                    WeatherScreenContent(
                        uiState = uiState,
                        modifier = modifier,
                        viewModel = viewModel
                    )

                }
            }
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreenContent(
    uiState: WeatherUiState,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel?,
) {
    when {
        uiState.isLoading -> {
            Animation(modifier = Modifier.fillMaxSize(), animation = R.raw.loading_animation)
        }

        uiState.errorMessage.isNotEmpty() -> {
            WeatherErrorState(uiState = uiState, viewModel = viewModel)
        }

        else -> {
            WeatherSuccessState(modifier = modifier, uiState = uiState)
        }
    }
}

@Composable
private fun WeatherErrorState(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    viewModel: WeatherViewModel?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Animation(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f),
            animation = R.raw.animation_error,
        )

        Button(onClick = { viewModel?.getWeather() }) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Retry",
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(R.string.retry),
                fontWeight = FontWeight.Bold,
            )
        }

        Text(
            modifier = modifier
                .weight(2f)
                .alpha(0.5f)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = "Something went wrong: ${uiState.errorMessage}",
            textAlign = TextAlign.Center,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherSuccessState(
    modifier: Modifier,
    uiState: WeatherUiState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(R.drawable.loctaion), "location icon")
            Spacer(modifier = Modifier.size(8.dp))
            Text(

                text = uiState.weather?.name.orEmpty(),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = stringResource(
                        R.string.icon_image_url,
                        uiState.weather?.condition?.icon.orEmpty(),
                    ),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_placeholder),
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                )

                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .align(Alignment.End),
                    text = uiState.weather?.condition?.text.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                )

            }

            Column(modifier = Modifier) {

                Text(
                    text = "Today",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                )

                Text(
                    text = stringResource(
                        R.string.temperature_value_in_celsius,
                        uiState.weather?.temperature.toString()
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 50.sp),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = stringResource(
                        R.string.min_temperature_in_celsius,
                        uiState.weather?.forecasts?.get(0)?.minTemp.toString()
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        R.string.max_temperature_in_celsius,
                        uiState.weather?.forecasts?.get(0)?.maxTemp.toString()
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )


            }


        }



        Text(
            modifier = Modifier.padding(4.dp),
            text = uiState.weather?.date?.toFormattedFullSpellDay().orEmpty(),
            style = MaterialTheme.typography.bodyLarge
        )


        Text(
            modifier = Modifier.padding(bottom = 4.dp, top = 4.dp),
            text = uiState.weather?.date?.toFormattedDate().orEmpty(),
            style = MaterialTheme.typography.bodySmall
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(id = R.drawable.ic_sunrise), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uiState.weather?.forecasts?.get(0)?.sunrise?.lowercase(Locale.US).orEmpty(),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.ic_sunset), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uiState.weather?.forecasts?.get(0)?.sunset?.lowercase(Locale.US).orEmpty(),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.wind_speed_label),
                weatherValue = uiState.weather?.wind.toString(),
                weatherUnit = stringResource(R.string.wind_speed_unit),
                iconId = R.drawable.ic_wind,
            )
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.feels_like),
                weatherValue = uiState.weather?.feelsLike.toString(),
                weatherUnit = "°C",
                iconId = R.drawable.ic_feels_like,
            )
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.humidity_label),
                weatherValue = uiState.weather?.humidity.toString(),
                weatherUnit = stringResource(R.string.humidity_unit),
                iconId = R.drawable.ic_humidity,
            )
        }

        Spacer(Modifier.height(16.dp))

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.forecast),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 8.dp, start = 16.dp),
        ) {
            uiState.weather?.let { weather ->
                items(weather.forecasts.drop(1)) { forecast ->
                    ForecastComponent(
                        date = forecast.date,
                        icon = forecast.icon,
                        minTemp = stringResource(
                            R.string.temperature_value_in_celsius,
                            forecast.minTemp
                        ),
                        maxTemp = stringResource(
                            R.string.temperature_value_in_celsius,
                            forecast.maxTemp,
                        ),
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true,
    device = PIXEL_XL
)
@Composable
fun WeatherScreenContentPreview() {
    val hourlyForecast = mutableListOf<Hour>()
    for (i in 0 until 24) {
        hourlyForecast.add(
            Hour(
                "yyyy-mm-dd ${String.format("%02d", i)}",
                "",
                "${Random.nextInt(18, 21)}"
            )
        )
    }
    val forecasts = mutableListOf<Forecast>()
    for (i in 0..9) {
        forecasts.add(
            Forecast(
                "2023-10-${String.format("%02d", i)}",
                "${Random.nextInt(18, 21)}",
                "${Random.nextInt(10, 15)}",
                "07:20 am",
                "06:40 pm",
                "",
                hourlyForecast
            )
        )
    }
    MyOpenWeatherTheme {
        Surface {
            WeatherScreenContent(
                WeatherUiState(
                    Weather(
                        temperature = 19,
                        date = "Oct 7",
                        wind = 22,
                        humidity = 35,
                        feelsLike = 18,
                        condition = Condition(10, "", "Cloudy"),
                        uv = 2,
                        name = "Munich",
                        forecasts = forecasts,
                    ),
                ), viewModel = null
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteCitySearchBar(
    searchText: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

//
//    var expand by remember { mutableStateOf(false) }
//    val cities = listOf(
//        "New York",
//        "London",
//        "Paris",
//        "Tokyo",
//        "Sydney",
//        "Delhi",
//        "Mumbai",
//        "Shanghai",
//        "Sao Paulo",
//        "Mexico City"
//    ) // Replace with your actual city data

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {

        TextField(
            value = searchText,
            onValueChange = { string ->
                onTextChange(string)
//                expand = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp)
                .clip(RoundedCornerShape(8.dp)).onFocusChanged {
                    isFocused = it.isFocused
                },

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),

            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(searchText)
                    isFocused=false
                    keyboardController?.hide()
//                    expand = false
                }
            ),

            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchText.isNotEmpty()) {
                            onTextChange("")

                        } else {
                            onCloseClicked()
                        }
                        isFocused=false
                        keyboardController?.hide()
//                        expand=false
                    }
                ) {
                    if(searchText.isNotEmpty())
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_icon),
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text(
                    text = "Search For City", // Placeholder text
                    color = Color.Gray, // Optional: Change placeholder color
                )
            }, leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier
                    = Modifier.padding(end = 4.dp)
                )
            }
        )

        // Drop-down menu for city suggestions
//        DropdownMenu(
//            expanded = expand/*searchText.text.isNotEmpty()*/,
//            onDismissRequest = { },
//            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp)
//        ) {
//
//          val temp = cities.filter { it.startsWith(searchText, true) }
//            if(temp.isNotEmpty()) {
//                temp.forEach { city ->
//
//                    DropdownMenuItem(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = { Text(text = city) },
//                        onClick = {
////                        searchText = TextFieldValue(city)
//                            onTextChange(city)
//                            onSearchClicked(city)
//                            expand = false
//                            isFocused=false
//                        }
//                    )
//                }
//            }else{
//                expand=false
//            }
//        }
    }
}

