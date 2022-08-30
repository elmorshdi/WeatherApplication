package com.elmorshdi.weatheraplication.domain.repository

import com.elmorshdi.weatheraplication.data.remote.WeatherDto
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double,
                               long: Double,
                               key:String,
                               lang :String,
                               unit:String
    ): Resource<WeatherInfo>

}