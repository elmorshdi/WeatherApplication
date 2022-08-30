package com.elmorshdi.weatheraplication.data.cachedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
 import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import java.util.*
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: WeatherInfo)
    @Query("DELETE FROM weather_table")
   suspend fun deleteAll()
    @Query("SELECT * FROM weather_table ")
    suspend fun getAll(): Flow<List<WeatherInfo>>

}
