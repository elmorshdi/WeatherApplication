package com.elmorshdi.weatheraplication.data.repository

import com.elmorshdi.weatheraplication.data.cachedata.WeatherDao
import com.elmorshdi.weatheraplication.data.cachedata.model.DbModel
import com.elmorshdi.weatheraplication.data.remote.WeatherApi
import com.elmorshdi.weatheraplication.data.remote.model.WeatherDto
import com.elmorshdi.weatheraplication.domain.repository.WeatherRepository
import com.elmorshdi.weatheraplication.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeatherData(
        lat: Double,
        long: Double,
        key: String,
        lang: String,
        unit: String
    ): Resource<WeatherDto> {

        val response = api.getWeatherData(lat, long, key, lang, unit)

        return try {
            if (response.isSuccessful) {

                Resource.Success(data = response.body()!!)


            } else {
                Resource.Error(
                    response.message().toString()
                )
            }


        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getCashedWeatherInfo(): Flow<List<DbModel>> {

        return dao.getAll()
    }

    override suspend fun deleteCashedWeatherInfo(): Resource<DbModel> {
        return try {
            dao.deleteAll()
            Resource.Success(null)
        } catch (
            e: Exception
        ) {
            Resource.Error(e.message.toString())

        }


    }

    override suspend fun addWeatherInfoToDB(weatherInfo: DbModel): Resource<DbModel> {
        return try {
            dao.insert(weatherInfo)
            Resource.Success(null)
        } catch (
            e: Exception
        ) {
            Resource.Error(e.message.toString())

        }
    }
}