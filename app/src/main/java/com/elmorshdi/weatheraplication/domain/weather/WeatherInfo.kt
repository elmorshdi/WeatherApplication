package com.elmorshdi.weatheraplication.domain.weather

import com.elmorshdi.weatheraplication.data.remote.Weather
import org.intellij.lang.annotations.Language

data class WeatherInfo (
    val cityName:String,
    val weatherDataPerDate : List<WeatherByDay>,
    val currentWeatherData : WeatherData
     )





