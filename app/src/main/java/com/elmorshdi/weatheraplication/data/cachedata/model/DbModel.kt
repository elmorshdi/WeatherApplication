package com.elmorshdi.weatheraplication.data.cachedata.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Weather_table")
@TypeConverters
data class DbModel(
    val city: String?,
    val list: List<ForecastDb?>?,
    @PrimaryKey(autoGenerate = true)
    val id: Int?
)


