package com.example.weatherapp2025.provider

import android.location.Location

interface LocationProvider {
    fun getLastLocation(
        onSuccess: (Location?) -> Unit,
        onFailure: (Exception) -> Unit
    )
}