package com.elmorshdi.weatheraplication.data.mappers

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.elmorshdi.weatheraplication.domain.weather.WeatherByDay
import com.elmorshdi.weatheraplication.domain.weather.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
class DataConverter {
    @TypeConverter
    fun fromWeatherByDay(weatherByDayList: List<WeatherByDay?>?): String? {
        if (weatherByDayList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherByDay?>?>() {}.type
        return gson.toJson(weatherByDayList, type)
    }

    @TypeConverter
    fun toWeatherByDay(weatherByDayListString: String?): List<WeatherByDay>? {
        if (weatherByDayListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherByDay?>?>() {}.type
        return gson.fromJson<List<WeatherByDay>>(weatherByDayListString, type)
    }

    @TypeConverter
    fun fromListWeatherData(weatherDataList:List<WeatherData?>?): String? {
        if (weatherDataList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherData?>?>() {}.type
        return gson.toJson(weatherDataList, type)
    }

    @TypeConverter
    fun toListWeatherData(weatherDataListString: String?): List<WeatherData>?{
        if (weatherDataListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherData?>>() {}.type
        return gson.fromJson<List<WeatherData>>(weatherDataListString, type)
    }
    @TypeConverter
    fun fromWeatherData(weatherDataList: WeatherData?): String? {
        if (weatherDataList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherData?>() {}.type
        return gson.toJson(weatherDataList, type)
    }

    @TypeConverter
    fun toWeatherData(weatherDataListString: String?): WeatherData?{
        if (weatherDataListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherData?>() {}.type
        return gson.fromJson<WeatherData>(weatherDataListString, type)
    }
}