package com.elmorshdi.weatheraplication.domain.weather

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.elmorshdi.weatheraplication.data.remote.Weather
import kotlinx.parcelize.Parcelize
import org.intellij.lang.annotations.Language
@Entity(tableName = "Weather_table")
@TypeConverters
 data class WeatherInfo (
    @PrimaryKey
    val cityName:String,
    val weatherDataPerDate : List<WeatherByDay>,
    val currentWeatherData : WeatherData
     )





