package com.elmorshdi.weatheraplication.data.remote

import com.elmorshdi.weatheraplication.data.remote.WeatherDto
import org.intellij.lang.annotations.Language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WeatherApi {
    @POST("data/2.5/forecast")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiKey: String,
        @Query("lang") language: String,
        @Query("units") units :String
    ):Response<WeatherDto>
}