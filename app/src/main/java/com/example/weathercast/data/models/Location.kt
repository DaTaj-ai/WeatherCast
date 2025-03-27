package com.example.weathercast.data.models

import androidx.room.Entity


@Entity(tableName = "locations", primaryKeys = ["latitude", "longitude"])
class Location(
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val country: String
)
