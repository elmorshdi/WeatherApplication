package com.elmorshdi.weatheraplication.data.cachedata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elmorshdi.weatheraplication.data.cachedata.model.DbModel

@Database(
    entities = [DbModel::class], // Tell the database the entries will hold data of this type
    version = 2
)
@TypeConverters(DataConverter::class)
abstract class  MyDataBase : RoomDatabase() {

        abstract fun getDao(): WeatherDao
    }
