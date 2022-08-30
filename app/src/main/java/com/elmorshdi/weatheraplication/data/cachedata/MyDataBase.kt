package com.elmorshdi.weatheraplication.data.cachedata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo

@Database(
    entities = [WeatherInfo::class], // Tell the database the entries will hold data of this type
    version = 1
)
abstract class  MyDataBase : RoomDatabase() {

        abstract fun getDao(): WeatherDao
    }