package com.example.weatherapp2025.provider

import android.content.Context

class DefaultStringProvider(private val context: Context) : StringProvider {
    override fun getString(resId: Int): String = context.getString(resId)
}