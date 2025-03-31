package com.example.lab4workmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface   WeatherDao {

    // Location part
    @Upsert
    fun insertLocation(location: Location):Long
    @Delete()
     fun deleteLocation(location: Location):Int

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<Location>>

    @Upsert
    fun insertWeather(weatherModel: WeatherModel):Long

    @Delete()
    fun deleteWeather(weatherModel: WeatherModel):Int

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): Flow<List<WeatherModel>>

    @Query("SELECT * FROM weather_table WHERE id = :myId")
    fun getByid(myId :Int):Flow<WeatherModel>
}