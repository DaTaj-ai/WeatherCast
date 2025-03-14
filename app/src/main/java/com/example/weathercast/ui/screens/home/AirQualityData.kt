package com.example.weathercast.ui.screens.home

import androidx.annotation.DrawableRes
import com.example.weathercast.R

data class AirQualityItem(
    @DrawableRes val icon: Int,
    val title: String,
    val value: String
)

val AirQualityData = listOf(
    AirQualityItem(
        title = "Real Feel",
        value = "23.8",
        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
    ),
    AirQualityItem(
        title = "Wind",
        value = "9km/h",
        icon = R.drawable.cloud_day_forecast_rain_rainy_icon,
    ),
    AirQualityItem(
        title = "SO2",
        value = "0.9",
        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
    ),
    AirQualityItem(
        title = "Rain",
        value = "68%",
        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
    ),
    AirQualityItem(
        title = "UV Index",
        value = "3",
        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
    ),
    AirQualityItem(
        title = "OЗ",
        value = "50",
        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
    )
)