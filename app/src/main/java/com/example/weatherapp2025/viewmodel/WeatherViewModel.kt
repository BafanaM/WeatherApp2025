package com.example.weatherapp2025.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp2025.R
import com.example.weatherapp2025.model.Daily
import com.example.weatherapp2025.model.WeatherResponse
import com.example.weatherapp2025.network.RetrofitClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _cityName = mutableStateOf("Fetching city...")
    val cityName: State<String> = _cityName

    private val _weatherData = mutableStateOf<WeatherResponse?>(null)
    val weatherData: State<WeatherResponse?> = _weatherData

    private val _dailyForecast = mutableStateOf<List<Daily>?>(null)
    val dailyForecast: State<List<Daily>?> = _dailyForecast

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(application)
    }

    fun fetchCityName(context: Context) {
        val permission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            _cityName.value = context.getString(R.string.permission_denied)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
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
                _cityName.value = context.getString(R.string.location_not_found)
                _weatherData.value = null
                _dailyForecast.value = null
                _isRefreshing.value = false
            }
        }.addOnFailureListener {
            _cityName.value = context.getString(R.string.error_fetching_location)
            _weatherData.value = null
            _dailyForecast.value = null
            _isRefreshing.value = false
        }
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

        val permission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            _cityName.value = context.getString(R.string.permission_denied)
            _weatherData.value = null
            _dailyForecast.value = null
            _isRefreshing.value = false
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
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
                _cityName.value = context.getString(R.string.location_not_found)
                _weatherData.value = null
                _dailyForecast.value = null
                _isRefreshing.value = false
            }
        }.addOnFailureListener {
            _cityName.value = context.getString(R.string.error_fetching_location)
            _weatherData.value = null
            _dailyForecast.value = null
            _isRefreshing.value = false
        }
    }
}