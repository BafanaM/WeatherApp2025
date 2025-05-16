package com.example.weatherapp2025.provider

import android.content.Context

interface LocationPermissionChecker {
    fun isLocationPermissionGranted(context: Context): Boolean
}
