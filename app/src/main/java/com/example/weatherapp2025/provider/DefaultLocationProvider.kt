package com.example.weatherapp2025.provider

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices

class DefaultLocationProvider(context: Context) : LocationProvider {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun getLastLocation(
        onSuccess: (Location?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onFailure)
    }
}