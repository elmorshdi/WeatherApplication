package com.elmorshdi.weatheraplication.di

import com.elmorshdi.weatheraplication.BuildConfig
import com.elmorshdi.weatheraplication.data.remote.WeatherApi
import com.elmorshdi.weatheraplication.view.util.Constants
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL).addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            ).client(okHttpClient).build()
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder().addInterceptor(
            LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()
        ).build()
    } else {
        OkHttpClient.Builder().build()
    }
}