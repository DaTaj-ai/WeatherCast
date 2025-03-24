package com.example.weathercast.data.models

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathercast.ui.screens.home.components.ForecastItem
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Entity(tableName = "forecast")

data class ForecastModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var city: City,
    var cnt: Int,
    var cod: String,
    @SerializedName("list")
    var forecastEntry: List<ForecastEntry>,
    var message: Int
)


//fun ForecastModel.getTodayForecast(): List<DailyForecast> {
//    var mylist = mutableListOf<DailyForecast>()
//    val date = SimpleDateFormat("MM-dd", Locale.getDefault()).format(java.util.Date())
//    for (i in 1..8) {
//        var DailyForecast: DailyForecast = DailyForecast(
//            forecastEntry.get(i).dt_txt,
//            forecastEntry.get(i).main.temp.toString(),
//            ""
//        )
//        mylist.add(DailyForecast)
//    }
//    return mylist
//}

fun ForecastModel.getTodayForecast(): List<DailyForecast> {
    val mylist = mutableListOf<DailyForecast>()
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault()) // Format for "12:00 PM" or "3:00 PM"

    for (i in 1..8) {
        val dateTimeString = forecastEntry[i].dt_txt
        val date: Date = inputFormat.parse(dateTimeString) // Parse the date string
        val formattedTime = outputFormat.format(date) // Format the time as "12:00 PM" or "3:00 PM"

        val dailyForecast = DailyForecast(
            date = formattedTime, // Use the formatted time
            temperature = forecastEntry[i].main.temp.toString(),
            icon = ""
        )
        mylist.add(dailyForecast)
    }
    return mylist
}


fun ForecastModel.dailyForecasts(): List<ForecastItem> {
    val dailyForecastList = mutableListOf<ForecastItem>()

    for (i in forecastEntry.indices step 8) {
        val item = forecastEntry[i]


        val date = Date(item.dt * 1000L) // Convert timestamp to Date object
        val dateFormat = SimpleDateFormat("dd-MM", Locale.getDefault()) // Specify the format you want (day-month)
        val dayMonth = dateFormat.format(date) // Format the date

        val dayOfWeekFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeek = dayOfWeekFormat.format(date)


//            // Create a ForecastItem for this forecast
//            val forecastItem = ForecastItem(
//                /*item.weather[0].icon.toInt(),*/ // icon (ensure it is an integer)
//                dayMonth, // date-time string
//                item.main.temp.toString(), // temperature
//                item.main.humidity.toString(), // humidity
//                item.wind.speed.toString(), // wind speed
//                item.clouds.all.toString() // cloudiness
//            )
        val forecastItem = ForecastItem(
            dayOfWeek = dayOfWeek,
            date = dayMonth,
            temperature = item.main.temp.toString(),
            description = item.weather[0].description,
            icon = item.weather[0].icon,
        )

            dailyForecastList.add(forecastItem)

    }

    return dailyForecastList
}

