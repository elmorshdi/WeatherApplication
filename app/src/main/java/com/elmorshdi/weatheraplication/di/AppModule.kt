package com.elmorshdi.weatheraplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.elmorshdi.weatheraplication.data.cachedata.MyDataBase
import com.elmorshdi.weatheraplication.data.cachedata.WeatherDao
import com.elmorshdi.weatheraplication.data.cachedata.DataConverter
import com.elmorshdi.weatheraplication.data.remote.WeatherApi
import com.elmorshdi.weatheraplication.view.util.Constants.BASE_URL
import com.elmorshdi.weatheraplication.view.util.Constants.DB_NAME
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MyDataBase::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration()
        .addTypeConverter(DataConverter())
        .build()

    @Singleton
    @Provides
    fun provideYourDao(db: MyDataBase) :WeatherDao = db.getDao()
}
