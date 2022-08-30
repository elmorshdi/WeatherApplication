package com.elmorshdi.weatheraplication.domain.weather

import java.time.LocalDateTime

data class WeatherData (
    val time :TimeFormat,
    val temperature :Double,
     val windSpeed:Double,
    val humidity :Int,
    val feelsLike :Double,

    val weatherDescription :String,
    val weatherType: WeatherType
        )
data class TimeFormat(
    val H:Int,
    val M:Int
)