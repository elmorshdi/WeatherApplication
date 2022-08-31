package com.elmorshdi.weatheraplication.domain.repository

import com.elmorshdi.weatheraplication.data.remote.WeatherDto
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double,
                               long: Double,
                               key:String,
                               lang :String,
                               unit:String
    ): Resource<WeatherInfo>
    suspend fun getCashedWeatherInfo():Flow<List<WeatherInfo>>
   suspend fun deleteCashedWeatherInfo(): Resource<WeatherInfo>
    suspend  fun addWeatherInfoToDB(weatherInfo:  WeatherInfo): Resource<WeatherInfo>
}