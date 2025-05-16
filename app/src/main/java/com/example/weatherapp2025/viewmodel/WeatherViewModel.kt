package com.example.weatherapp2025.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp2025.R
import com.example.weatherapp2025.model.Daily
import com.example.weatherapp2025.model.WeatherResponse
import com.example.weatherapp2025.network.RetrofitClient
import com.example.weatherapp2025.provider.LocationPermissionChecker
import com.example.weatherapp2025.provider.LocationProvider
import com.example.weatherapp2025.provider.StringProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val application: Application,
    private val locationPermissionChecker: LocationPermissionChecker,
    private val stringProvider: StringProvider,
    private val locationProvider: LocationProvider
) : AndroidViewModel(application) {


    private val _cityName = mutableStateOf("Fetching city...")
    val cityName: State<String> = _cityName

    private val _weatherData = mutableStateOf<WeatherResponse?>(null)
    val weatherData: State<WeatherResponse?> = _weatherData

    private val _dailyForecast = mutableStateOf<List<Daily>?>(null)
    val dailyForecast: State<List<Daily>?> = _dailyForecast

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    fun fetchCityName(context: Context) {
        if (!locationPermissionChecker.isLocationPermissionGranted(context)) {
            _cityName.value = stringProvider.getString(R.string.permission_denied)
            return
        }

        locationProvider.getLastLocation(
            onSuccess = { location ->
                if (location != null) {
                    val lat = location.latitude.toString()
                    val lon = location.longitude.toString()

                    viewModelScope.launch {
                        try {
                            _isRefreshing.value = true
                            val currentJob = async { fetchCurrentWeather(lat, lon) }
                            val forecastJob = async { fetchForecast(lat, lon) }
                            currentJob.await()
                            forecastJob.await()
                        } finally {
                            _isRefreshing.value = false
                        }
                    }
                } else {
                    _cityName.value = stringProvider.getString(R.string.location_not_found)
                    _weatherData.value = null
                    _dailyForecast.value = null
                    _isRefreshing.value = false
                }
            },
            onFailure = {
                _cityName.value = stringProvider.getString(R.string.error_fetching_location)
                _weatherData.value = null
                _dailyForecast.value = null
                _isRefreshing.value = false
            }
        )
    }

    suspend fun fetchCurrentWeather(lat: String, lon: String) {
        try {
            val response = RetrofitClient.api.getWeatherByLocation(lat, lon)
            if (response.isSuccessful) {
                response.body()?.let { weather ->
                    _cityName.value = weather.name ?: "Unknown"
                    _weatherData.value = weather
                } ?: run {
                    _cityName.value = "No weather data"
                    _weatherData.value = null
                }
            } else {
                _cityName.value = "Error fetching city"
                _weatherData.value = null
            }
        } catch (e: Exception) {
            _cityName.value = "Network error"
            _weatherData.value = null
        }
    }

    private suspend fun fetchForecast(lat: String, lon: String) {
        try {
            val exclude = "current,minutely,hourly"
            val response = RetrofitClient.api.getWeatherForecast(lat, lon, exclude)
            if (response.isSuccessful) {
                _dailyForecast.value = response.body()?.daily
            } else {
                _dailyForecast.value = null
            }
        } catch (e: Exception) {
            _dailyForecast.value = null
        }
    }

    fun refreshWeather(context: Context) {
        _isRefreshing.value = true

        if (!locationPermissionChecker.isLocationPermissionGranted(context)) {
            _cityName.value = stringProvider.getString(R.string.permission_denied)
            _weatherData.value = null
            _dailyForecast.value = null
            _isRefreshing.value = false
            return
        }

        locationProvider.getLastLocation(
            onSuccess = { location ->
                if (location != null) {
                    val lat = location.latitude.toString()
                    val lon = location.longitude.toString()

                    viewModelScope.launch {
                        try {
                            val currentJob = async { fetchCurrentWeather(lat, lon) }
                            val forecastJob = async { fetchForecast(lat, lon) }
                            currentJob.await()
                            forecastJob.await()
                        } finally {
                            _isRefreshing.value = false
                        }
                    }
                } else {
                    _cityName.value = stringProvider.getString(R.string.location_not_found)
                    _weatherData.value = null
                    _dailyForecast.value = null
                    _isRefreshing.value = false
                }
            },
            onFailure = {
                _cityName.value = stringProvider.getString(R.string.error_fetching_location)
                _weatherData.value = null
                _dailyForecast.value = null
                _isRefreshing.value = false
            }
        )
    }
}