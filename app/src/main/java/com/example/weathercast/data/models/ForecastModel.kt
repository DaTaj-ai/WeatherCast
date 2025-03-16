package com.example.weathercast.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "forecast")

data class ForecastModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int ,
    var city: City,
    var cnt: Int,
    var cod: String,
    var list: List<Item0>,
    var message: Int
)