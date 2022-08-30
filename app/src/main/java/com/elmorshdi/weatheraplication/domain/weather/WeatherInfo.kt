package com.elmorshdi.weatheraplication.domain.weather

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elmorshdi.weatheraplication.data.remote.Weather
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.intellij.lang.annotations.Language
@Entity(tableName = "weather_table")
@Parcelize
data class WeatherInfo (
    @PrimaryKey
    @SerializedName("cityName")
    val cityName:String,
    @SerializedName("weatherDataPerDateList")
    val weatherDataPerDate : List<WeatherByDay>,
    @SerializedName("weatherData")

    val currentWeatherData : WeatherData
     ) : Parcelable





