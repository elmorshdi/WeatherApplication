package com.elmorshdi.weatheraplication.domain.weather

data class WeatherByDay(
    val data: String,
    val weather: ArrayList<WeatherData>
)