package com.elmorshdi.weatheraplication.data.cachedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmorshdi.weatheraplication.data.cachedata.model.DbModel
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherInfo: DbModel)

    @Query("DELETE FROM Weather_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM Weather_table ")
     fun getAll(): Flow<List<DbModel>>

}
