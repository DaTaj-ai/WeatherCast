package com.example.weathercast.ui.screens.home

import androidx.annotation.DrawableRes
import com.example.weathercast.R
import com.example.weathercast.data.models.WeatherModel
import java.time.LocalTime

data class AirQualityItem(
    @DrawableRes val icon: Int,
    val title: String,
    val value: String,
)

//// date
//Log.i("TAG", "DailyForecast: ${getDate(weather!!.dt)}")
//
    //// Humidity
//Log.i("TAG", "DailyForecast: ${weather!!.main.humidity}")
//
//// Wind speed
//Log.i("TAG", "DailyForecast: ${weather!!.wind.speed}")
//
//// pressure
//Log.i("TAG", "DailyForecast: ${weather!!.main.pressure}")
//
//// clouds
//Log.i("TAG", "DailyForecast: ${weather!!.clouds.all}")
//
////city
//Log.i("TAG", "DailyForecast: ${weather!!.name}")
//
////Description
//Log.i("TAG", "DailyForecast: ${weather!!.weather.get(0).description}")
//
//// current  Hour and Minute
//val currentTime = LocalTime.now()
//val hours = currentTime.hour
//val minutes = currentTime.minute
//println("Current time: $hours:$minutes")


//val AirQualityData = listOf(
//    AirQualityItem(
//        title = "Pressure",
//        value = ,
//        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
//    ),
//    AirQualityItem(
//        title = "Wind",
//        value = "9km/h",
//        icon = R.drawable.cloud_day_forecast_rain_rainy_icon,
//    ),
//    AirQualityItem(
//        title = "SO2",
//        value = "0.9",
//        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
//    ),
//    AirQualityItem(
//        title = "Rain",
//        value = "68%",
//        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
//    ),
//    AirQualityItem(
//        title = "UV Index",
//        value = "3",
//        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
//    ),
//    AirQualityItem(
//        title = "OЗ",
//        value = "50",
//        icon = R.drawable.cloud_day_forecast_rain_rainy_icon
//    )
//)