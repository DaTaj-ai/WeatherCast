package com.example.lab4workmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weathercast.data.models.ForecastConverters
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.WeatherTypeConverters

@Database(entities = arrayOf(WeatherModel::class , Location::class), version = 2 )
@TypeConverters(WeatherTypeConverters::class, ForecastConverters::class)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    companion object{
        @Volatile
        private var INSTANCE: WeatherDataBase? = null

        fun getInstance (ctx: Context): WeatherDataBase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, WeatherDataBase::class.java, "WeatherDatabasev1")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
