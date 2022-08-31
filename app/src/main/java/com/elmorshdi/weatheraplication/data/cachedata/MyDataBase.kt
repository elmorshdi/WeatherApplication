package com.elmorshdi.weatheraplication.data.cachedata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elmorshdi.weatheraplication.data.mappers.DataConverter
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo

@Database(
    entities = [WeatherInfo::class], // Tell the database the entries will hold data of this type
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class  MyDataBase : RoomDatabase() {

        abstract fun getDao(): WeatherDao
    }
