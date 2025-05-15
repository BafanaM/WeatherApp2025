package com.example.weatherapp2025.network

import android.content.Context
import com.example.weatherapp2025.utils.APP_ID
import com.example.weatherapp2025.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class QueryParameterAddInterceptor(private val context: Context) : Interceptor {

    private val prefManager = PreferenceManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("appid", APP_ID)
            .addQueryParameter("units", prefManager.tempUnit)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}