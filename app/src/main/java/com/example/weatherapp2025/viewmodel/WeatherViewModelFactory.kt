package com.example.weatherapp2025.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp2025.provider.LocationPermissionChecker
import com.example.weatherapp2025.provider.LocationProvider
import com.example.weatherapp2025.provider.StringProvider

class WeatherViewModelFactory(
    private val app: Application,
    private val locationPermissionChecker: LocationPermissionChecker,
    private val stringProvider: StringProvider,
    private val locationProvider: LocationProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                app,
                locationPermissionChecker,
                stringProvider,
                locationProvider
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}