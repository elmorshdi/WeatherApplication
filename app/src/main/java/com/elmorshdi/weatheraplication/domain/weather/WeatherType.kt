package com.elmorshdi.weatheraplication.domain.weather

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.elmorshdi.weatheraplication.R
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class WeatherType(
    @DrawableRes val iconRes: Int
) : Parcelable {
    object ClearSkyD: WeatherType(
        iconRes = R.drawable._1d
    )
    object ClearSkyN: WeatherType(
        iconRes = R.drawable._1n
    )
    object FewCloudsD: WeatherType(
        iconRes = R.drawable._2d
    )
    object FewCloudsN: WeatherType(
        iconRes = R.drawable._2n
    )
    object ScatteredClouds: WeatherType(
        iconRes = R.drawable._3d
    )

    object BrokenClouds: WeatherType(
        iconRes = R.drawable._4d
    )

    object ShowerRain : WeatherType(
        iconRes = R.drawable._9d
    )

    object RainD : WeatherType(
        iconRes = R.drawable._10d
    )
    object RainN : WeatherType(
        iconRes = R.drawable._10n
    )

    object Thunderstorm : WeatherType(
        iconRes = R.drawable._11d
    )

    object Snow : WeatherType(
        iconRes = R.drawable._13d
    )

    object Mist : WeatherType(
        iconRes = R.drawable._0d
    )


    companion object {
        fun fromWMO(code: String): WeatherType {
            return when (code) {
               "01d" -> ClearSkyD
                "02d" -> FewCloudsD
                "03d"-> ScatteredClouds
                "04d" -> BrokenClouds
                "09d" -> ShowerRain
                "10d"-> RainD
                "11d" -> Thunderstorm
                "13d" -> Snow
                "50d"-> Mist
                "01n" -> ClearSkyN
                "02n" -> FewCloudsN
                "03n"-> ScatteredClouds
                "04n" -> BrokenClouds
                "09n" -> ShowerRain
                "10n"-> RainN
                "11n" -> Thunderstorm
                "13n" -> Snow
                "50n"-> Mist
                else -> ClearSkyD
            }
        }
    }
}