package com.example.weathercast.utlis

import com.example.weathercast.data.models.ForecastEntry
import com.example.weathercast.data.models.ForecastModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getTodaysForecast(forecast: ForecastModel): List<ForecastEntry> {
    val currentDate = Calendar.getInstance()
    val currentDateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate.time)


    return forecast.forecastEntry.filter {
        val forecastDateString = it.dt_txt.split(" ")[0] // Extract the date part from `dt_txt`
        forecastDateString == currentDateString
    }.take(8)
}