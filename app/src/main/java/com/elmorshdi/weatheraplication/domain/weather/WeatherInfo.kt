package com.elmorshdi.weatheraplication.domain.weather

data class WeatherInfo (
     val cityName:String,
    val weatherDataPerDate : List<WeatherByDay>,
    val currentWeatherData : WeatherData
     )





