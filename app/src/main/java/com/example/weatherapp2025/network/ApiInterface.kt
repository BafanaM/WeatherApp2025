package com.example.weatherapp2025.network

import com.example.weatherapp2025.model.WeatherForecastResponse
import com.example.weatherapp2025.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat")
        latitude:String,
        @Query("lon")
        longitude:String
    ): Response<WeatherResponse>

    @GET("onecall")
    suspend fun getWeatherForecast(
        @Query("lat")
        latitude:String,
        @Query("lon")
        longitude:String,
        @Query("exclude")
        exclude:String
    ): Response<WeatherForecastResponse>

}