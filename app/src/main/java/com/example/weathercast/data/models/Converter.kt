package com.example.weathercast.data.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    // Converter for City object
    @TypeConverter
    fun fromCity(city: City): String {
        return gson.toJson(city)
    }

    @TypeConverter
    fun toCity(cityJson: String): City {
        return gson.fromJson(cityJson, City::class.java)
    }

    // Converter for List<Item0>
    @TypeConverter
    fun fromItemList(list: List<ForecastEntry>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toItemList(listJson: String): List<ForecastEntry> {
        val type = object : TypeToken<List<ForecastEntry>>() {}.type
        return gson.fromJson(listJson, type)
    }
}