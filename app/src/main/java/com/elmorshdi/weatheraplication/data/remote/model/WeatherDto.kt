package com.elmorshdi.weatheraplication.data.remote.model


data class WeatherDto(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<Forecast?>?,
    val message: Int?
)
data class City(
    val coord: Coord?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)
data class Coord(
    val lat: Double?,
    val lon: Double?
)