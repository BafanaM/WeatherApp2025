package com.example.weatherapp2025

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp2025.network.RetrofitClient
import com.example.weatherapp2025.provider.DefaultLocationPermissionChecker
import com.example.weatherapp2025.provider.DefaultLocationProvider
import com.example.weatherapp2025.provider.DefaultStringProvider
import com.example.weatherapp2025.provider.LocationPermissionChecker
import com.example.weatherapp2025.provider.LocationProvider
import com.example.weatherapp2025.provider.StringProvider
import com.example.weatherapp2025.ui.theme.WeatherApp2025Theme
import com.example.weatherapp2025.view.WeatherScreen
import com.example.weatherapp2025.viewmodel.WeatherViewModel
import com.example.weatherapp2025.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: WeatherViewModel

    // Permission launcher
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.fetchCityName(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RetrofitClient.init(applicationContext)

        val locationPermissionChecker: LocationPermissionChecker = DefaultLocationPermissionChecker()
        val stringProvider: StringProvider = DefaultStringProvider(applicationContext)
        val locationProvider: LocationProvider = DefaultLocationProvider(applicationContext)

        val factory = WeatherViewModelFactory(
            application,
            locationPermissionChecker,
            stringProvider,
            locationProvider
        )

        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]

        enableEdgeToEdge()

        setContent {
            WeatherApp2025Theme {
                val city by viewModel.cityName
                WeatherScreen(
                    cityName = city ?: "Loading...",
                    viewModel = viewModel
                )
            }
        }

        // Check for location permission and request if necessary
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.fetchCityName(this)
        }
    }
}