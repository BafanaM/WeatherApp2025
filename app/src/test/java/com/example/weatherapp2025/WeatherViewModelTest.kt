package com.example.weatherapp2025

import android.app.Application
import android.content.Context
import com.example.weatherapp2025.provider.LocationPermissionChecker
import com.example.weatherapp2025.provider.LocationProvider
import com.example.weatherapp2025.provider.StringProvider
import com.example.weatherapp2025.viewmodel.WeatherViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @Mock
    lateinit var mockPermissionChecker: LocationPermissionChecker
    @Mock lateinit var mockStringProvider: StringProvider
    @Mock lateinit var mockLocationProvider: LocationProvider
    @Mock lateinit var mockContext: Context

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        val app = mock(Application::class.java)
        viewModel = WeatherViewModel(app, mockPermissionChecker, mockStringProvider, mockLocationProvider)
    }

    @Test
    fun `fetchCityName sets cityName to permission denied when permission is not granted`() {
        `when`(mockPermissionChecker.isLocationPermissionGranted(mockContext)).thenReturn(false)
        `when`(mockStringProvider.getString(R.string.permission_denied)).thenReturn("Permission Denied")

        viewModel.fetchCityName(mockContext)

        assertEquals("Permission Denied", viewModel.cityName.value)
    }
}