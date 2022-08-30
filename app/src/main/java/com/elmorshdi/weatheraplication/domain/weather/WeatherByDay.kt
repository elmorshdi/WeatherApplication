package com.elmorshdi.weatheraplication.domain.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherByDay(
    val data: String,
    val weather: ArrayList<WeatherData>
) : Parcelable