package com.elmorshdi.weatheraplication.domain.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData (
    val time :TimeFormat,
    val temperature :Double,
     val windSpeed:Double,
    val humidity :Int,
    val feelsLike :Double,

    val weatherDescription :String,
    val weatherType: WeatherType
        ) : Parcelable
@Parcelize
data class TimeFormat(
    val H:Int,
    val M:Int
) : Parcelable