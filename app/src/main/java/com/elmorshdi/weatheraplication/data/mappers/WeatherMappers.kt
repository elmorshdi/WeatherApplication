package com.elmorshdi.weatheraplication.data.mappers

import android.annotation.SuppressLint
import com.elmorshdi.weatheraplication.data.cachedata.model.DbModel
import com.elmorshdi.weatheraplication.data.cachedata.model.ForecastDb
import com.elmorshdi.weatheraplication.data.remote.model.WeatherDto
import com.elmorshdi.weatheraplication.domain.weather.*
import java.text.SimpleDateFormat
import java.util.*


fun WeatherDto.mapToWeatherByDay(): List<WeatherByDay> {
    val daysList: ArrayList<WeatherByDay> = arrayListOf()
    for (forecast in this.list!!) {
        val dateTimeTxt = forecast?.dt_txt
        val dateFormatted = convertToCustomFormat(dateTimeTxt!!)
        val weatherData = WeatherData(
            time = getTimeFormat(forecast.dt_txt),
            temperature = forecast.main?.temp!!,
            windSpeed = forecast.wind?.speed!!,
            humidity = forecast.main.humidity!!,
            feelsLike = forecast.main.feels_like!!,
            weatherDescription = forecast.weather?.get(0)?.description!!,
            weatherType = WeatherType.fromWMO(forecast.weather[0]?.icon!!)
        )

        val x = daysList.find {
            it.data == dateFormatted
        }
        if (x == null) {
            val current = WeatherByDay(
                data = dateFormatted,
                weather = arrayListOf()
            )
            current.weather.add(weatherData)
            daysList.add(current)
        } else {
            x.weather.add(
                weatherData
            )

        }


    }
    return daysList
}

fun DbModel.mapToWeatherByDay(): List<WeatherByDay> {
    val daysList: ArrayList<WeatherByDay> = arrayListOf()
    for (forecast in this.list!!) {
        val dateTimeTxt = forecast?.dt_txt
        val dateFormatted = convertToCustomFormat(dateTimeTxt!!)
        val weatherData = WeatherData(
            time = getTimeFormat(forecast.dt_txt),
            temperature = forecast.temp!!,
            windSpeed = forecast.windSpeed!!,
            humidity = forecast.humidity!!,
            feelsLike = forecast.feels_like!!,
            weatherDescription = forecast.description!!,
            weatherType = WeatherType.fromWMO(forecast.icon!!)
        )

        val x = daysList.find {
            it.data == dateFormatted
        }
        if (x == null) {
            val current = WeatherByDay(
                data = dateFormatted,
                weather = arrayListOf()
            )
            current.weather.add(weatherData)
            daysList.add(current)
        } else {
            x.weather.add(
                weatherData
            )

        }


    }
    return daysList
}

fun WeatherDto.mapToWeatherInfo(): WeatherInfo {
    val citName = this.city?.name
    val weatherDataMap = this.mapToWeatherByDay()
    val currentWeatherData = weatherDataMap[0].weather[0]
    return WeatherInfo(
        citName!!,
        currentWeatherData = currentWeatherData,
        weatherDataPerDate = weatherDataMap
    )
}

fun DbModel.mapToWeatherInfo(): WeatherInfo {
    val citName = this.city
    val weatherDataMap = this.mapToWeatherByDay()
    val currentWeatherData = weatherDataMap[0].weather[0]
    return WeatherInfo(
        citName!!,
        currentWeatherData = currentWeatherData,
        weatherDataPerDate = weatherDataMap
    )
}

@SuppressLint("SimpleDateFormat")
private fun convertToCustomFormat(dateStr: String): String {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("yyyy-M-D")
    val destFormat = SimpleDateFormat("EEE, dd MMM")
    sourceFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    return destFormat.format(convertedDate!!)
}

fun getTimeFormat(s: String): TimeFormat {
    // "dt_txt": "2022-08-30 06:00:00"
    val timeTxt = s.split(" ")[1]
    val h = timeTxt.split(":")[0].toInt()
    val m = timeTxt.split(":")[1].toInt()
    return TimeFormat(h, m)
}

fun WeatherDto.toForecastDb(): List<ForecastDb> {

    val daysList: ArrayList<ForecastDb> = arrayListOf()

    for (forecast in this.list!!) {

        daysList.add(
            ForecastDb(
                dt_txt = forecast?.dt_txt!!,
                feels_like = forecast.main?.feels_like,
                humidity = forecast.main?.humidity,
                temp = forecast.main?.temp,
                description = forecast.weather?.get(0)?.description,
                icon = forecast.weather?.get(0)?.icon,
                windSpeed = forecast.wind?.speed
            )
        )
    }
    return daysList
}

fun WeatherDto.mapToDbModel(): DbModel {
    val citName = this.city?.name
    return DbModel(
        citName!!,
        this.toForecastDb(),
        1
    )
}
