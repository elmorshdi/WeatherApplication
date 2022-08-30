package com.elmorshdi.weatheraplication.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.elmorshdi.weatheraplication.data.mappers.mapToWeatherInfo
import com.elmorshdi.weatheraplication.data.remote.WeatherApi
import com.elmorshdi.weatheraplication.domain.repository.WeatherRepository
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(
        lat: Double,
        long: Double,
        key: String,
        lang: String,
        unit: String
    ): Resource<WeatherInfo> {
        var r :Resource<WeatherInfo> =Resource.Error("fff")
        Log.d("tag", "$key:::$lang:::$unit:::$lat:::$long")

        val response = api.getWeatherData(lat, long, key, lang, unit)
        if(response.isSuccessful) {
            r =  Resource.Success(data = response.body()?.mapToWeatherInfo())

            Log.d("tag",  response.body()?.mapToWeatherInfo().toString())


        }
        else {
            Log.d("tag","tag"+response.message().toString()+
                    "::"+ (response.errorBody()?.string() ?: "n")+
                    "::"+response.code().toString()+
                    "::"+response.headers().toString())
                r = Resource.Error(response.message().toString()+"::"+ (response.errorBody()?.string() ?: "n"))
        }

return r
}
}