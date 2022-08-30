package com.elmorshdi.weatheraplication.data.mappers

import android.annotation.SuppressLint
import com.elmorshdi.weatheraplication.data.remote.WeatherDto
import com.elmorshdi.weatheraplication.domain.weather.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDto.mapToWeatherByDay(): List<WeatherByDay> {
    val daysList: ArrayList<WeatherByDay> = arrayListOf<WeatherByDay>()
    for (forecast in this.list!!) {
        val dateTimeTxt = forecast?.dt_txt
        val dateFormatted = convertToCustomFormat(dateTimeTxt!!)
        val weatherData = WeatherData(
            time = getTimeFormat(forecast.dt_txt) ,
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
            val curnt = WeatherByDay(
                data = dateFormatted,
                weather = arrayListOf<WeatherData>()
            )
            curnt.weather.add(weatherData)
            daysList.add(curnt)
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

private fun convertToCustomFormat(dateStr: String): String {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("yyyy-M-D")
    val destFormat = SimpleDateFormat("EEE,dd,MMM")
    sourceFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    return destFormat.format(convertedDate!!)
}
fun getTimeFormat (s:String):TimeFormat{
    // "dt_txt": "2022-08-30 06:00:00"
    val timeTxt =s.split(" ")[1]
    val h =timeTxt.split(":")[0].toInt()
    val m =timeTxt.split(":")[1].toInt()
return TimeFormat(h,m)
}
