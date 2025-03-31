package com.example.weathercast.data.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather_table"  )
data class WeatherModel(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    @PrimaryKey()
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun WeatherModel.toJsonObject() = Uri.encode(Gson().toJson(this))

class WeatherTypeConverters {
    private val gson = Gson()

    // Clouds converter
    @TypeConverter
    fun cloudsToJson(clouds: Clouds): String = gson.toJson(clouds)

    @TypeConverter
    fun jsonToClouds(json: String): Clouds = gson.fromJson(json, Clouds::class.java)

    // Coord converter
    @TypeConverter
    fun coordToJson(coord: Coord): String = gson.toJson(coord)

    @TypeConverter
    fun jsonToCoord(json: String): Coord = gson.fromJson(json, Coord::class.java)

//    // Main converter
//    @TypeConverter
//    fun mainToJson(main: Main): String = gson.toJson(main)

//    @TypeConverter
//    fun jsonToMain(json: String): Main = gson.fromJson(json, Main::class.java)

    // Wind converter
    @TypeConverter
    fun windToJson(wind: Wind): String = gson.toJson(wind)

    @TypeConverter
    fun jsonToWind(json: String): Wind = gson.fromJson(json, Wind::class.java)

    // List<Weather> converter
    @TypeConverter
    fun weatherListToJson(weatherList: List<Weather>): String = gson.toJson(weatherList)

    @TypeConverter
    fun jsonToWeatherList(json: String): List<Weather> {
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(json, type)
    }

    // Sys converter (if you have Sys class)
    @TypeConverter
    fun sysToJson(sys: Sys): String = gson.toJson(sys)

    @TypeConverter
    fun jsonToSys(json: String): Sys = gson.fromJson(json, Sys::class.java)
}