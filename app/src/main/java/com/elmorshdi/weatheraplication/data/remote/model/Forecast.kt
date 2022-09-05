package com.elmorshdi.weatheraplication.data.remote.model



data class Forecast (
    val clouds: Clouds?,
    val dt: Int?,
    val dt_txt: String?,
    val main: Main?,
    val pop: Double?,
    val sys: Sys?,
    val visibility: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?
)
data class Clouds(
    val all: Int?
)
data class Main(
    val feels_like: Double?,
    val grnd_level: Int?,
    val humidity: Int?,
    val pressure: Int?,
    val sea_level: Int?,
    val temp: Double?,
    val temp_kf: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)
data class Sys(
    val pod: String?
)

data class Wind(
    val deg: Int?,
    val gust: Double?,
    val speed: Double?
)
data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
)