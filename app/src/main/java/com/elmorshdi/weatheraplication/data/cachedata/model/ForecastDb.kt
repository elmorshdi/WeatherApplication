package com.elmorshdi.weatheraplication.data.cachedata.model

import com.elmorshdi.weatheraplication.data.remote.model.*

data class ForecastDb (
    val dt_txt: String?,
      val feels_like: Double?,
     val humidity: Int?,
     val temp: Double?,
     val description: String?,
    val icon: String?,
    val windSpeed: Double?

)