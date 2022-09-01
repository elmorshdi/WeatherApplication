package com.elmorshdi.weatheraplication.data.cachedata

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.elmorshdi.weatheraplication.data.cachedata.model.ForecastDb
import com.elmorshdi.weatheraplication.domain.weather.WeatherByDay
import com.elmorshdi.weatheraplication.domain.weather.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
class DataConverter {





    @TypeConverter
    fun fromListWeatherData(weatherDataList:List<ForecastDb?>?): String? {
        if (weatherDataList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastDb?>?>() {}.type
        return gson.toJson(weatherDataList, type)
    }

    @TypeConverter
    fun toListWeatherData(weatherDataListString: String?): List<ForecastDb?>?{
        if (weatherDataListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastDb?>?>() {}.type
        return gson.fromJson<List<ForecastDb?>?>(weatherDataListString, type)
    }









}