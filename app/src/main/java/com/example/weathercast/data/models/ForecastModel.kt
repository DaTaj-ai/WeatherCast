package com.example.weathercast.data.models

data class ForecastModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item0>,
    val message: Int
)