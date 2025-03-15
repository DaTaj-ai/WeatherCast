package com.example.lab4workmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab4workmanager.models.Product

@Database(entities = arrayOf(Product::class), version = 1 )
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
