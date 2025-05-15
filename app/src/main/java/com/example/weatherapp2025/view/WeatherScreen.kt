package com.example.weatherapp2025.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp2025.R
import com.example.weatherapp2025.ui.theme.ColorBackground
import com.example.weatherapp2025.view.components.ActionBar
import com.example.weatherapp2025.view.components.DailyForecast
import com.example.weatherapp2025.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(cityName: String, viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val currentWeather = viewModel.weatherData.value
    val dailyForecast = viewModel.dailyForecast.value
    val isRefreshingState = viewModel.isRefreshing.value

    val dateFormat = remember { SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()) }

    val forecastDescription = currentWeather?.weather?.firstOrNull()?.main ?: "N/A"
    val currentTemp = currentWeather?.main?.temp?.roundToInt()?.toString() ?: "--"
    val feelsLike = currentWeather?.main?.feelsLike?.roundToInt()?.toString() ?: "--"
    val feelsLikeDesc = stringResource(R.string.feels_like, feelsLike)
    val formattedDate = currentWeather?.dt?.let { dateFormat.format(Date(it * 1000L)) } ?: stringResource(
        R.string.today
    )

    val isError = cityName == stringResource(R.string.permission_denied) ||
            cityName == stringResource(R.string.location_not_found) ||
            cityName == stringResource(R.string.error_fetching_location) ||
            currentWeather == null

    val isLoading = isRefreshingState && !isError

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                ActionBar(location = cityName)
                Spacer(modifier = Modifier.height(12.dp))

                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    isError -> {
                        Text(
                            text = stringResource(R.string.weather_details_not_available),
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    else -> {
                        DailyForecast(
                            forecast = forecastDescription,
                            date = formattedDate,
                            degree = currentTemp,
                            description = feelsLikeDesc
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

            Button(
                onClick = { viewModel.refreshWeather(context) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(R.string.refresh),
                    color = Color.White
                )

                if (isRefreshingState) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(18.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }
            }
        }
    }
}