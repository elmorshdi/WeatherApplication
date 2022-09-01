package com.elmorshdi.weatheraplication.domain.repository

import com.elmorshdi.weatheraplication.data.cachedata.model.DbModel
import com.elmorshdi.weatheraplication.data.remote.model.WeatherDto
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double,
                               long: Double,
                               key:String,
                               lang :String,
                               unit:String
    ): Resource<WeatherDto>
    suspend fun getCashedWeatherInfo():Flow<List<DbModel>>
   suspend fun deleteCashedWeatherInfo(): Resource<DbModel>
    suspend  fun addWeatherInfoToDB(weatherInfo:  DbModel): Resource<DbModel>
}