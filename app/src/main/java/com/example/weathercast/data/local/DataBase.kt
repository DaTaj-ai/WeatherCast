package com.example.lab4workmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weathercast.data.models.Converters
import com.example.weathercast.data.models.ForecastModel

@Database(entities = arrayOf(ForecastModel::class), version = 1 )
@TypeConverters(Converters::class)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getProductDao(): WeatherDao

    companion object{
        @Volatile
        private var INSTANCE: WeatherDataBase? = null
        fun getInstance (ctx: Context): WeatherDataBase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, WeatherDataBase::class.java, "Weather_database")
                    .build()
                INSTANCE = instance
                instance }
        }
    }
}
