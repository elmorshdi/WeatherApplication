package com.elmorshdi.weatheraplication.domain.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

   data class WeatherInfo (
     val cityName:String,
    val weatherDataPerDate : List<WeatherByDay>,
    val currentWeatherData : WeatherData
     )





