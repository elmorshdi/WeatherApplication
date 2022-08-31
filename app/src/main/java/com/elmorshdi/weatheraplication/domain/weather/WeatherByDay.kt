package com.elmorshdi.weatheraplication.domain.weather

import androidx.room.TypeConverters

data class WeatherByDay(
    val data: String,
    val weather: ArrayList<WeatherData>
)