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


fun ForecastModel.getTodayForecast(): List<DailyForecast> {
    var mylist = mutableListOf<DailyForecast>()
    val date = SimpleDateFormat("MM-dd", Locale.getDefault()).format(java.util.Date())
    for (i in 1..8) {
        var DailyForecast: DailyForecast = DailyForecast(
            forecastEntry.get(i).dt_txt,
            forecastEntry.get(i).main.temp.toString(),
            ""
        )
        mylist.add(DailyForecast)
    }
    return mylist
}


fun ForecastModel.dailyForecasts(): List<ForecastItem> {
    val dailyForecastList = mutableListOf<ForecastItem>()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    for (i in forecastEntry.indices step 8) {
        val item = forecastEntry[i]


        val date = Date(item.dt * 1000L) // Convert timestamp to Date object
        val dateFormat = SimpleDateFormat("dd-MM", Locale.getDefault()) // Specify the format you want (day-month)
        val dayMonth = dateFormat.format(date) // Format the date

        // Check if the forecast time is exactly noon (12:00 PM)

            // Create a ForecastItem for this forecast
            val forecastItem = ForecastItem(
                /*item.weather[0].icon.toInt(),*/ // icon (ensure it is an integer)
                dayMonth, // date-time string
                item.main.temp.toString(), // temperature
                item.main.humidity.toString(), // humidity
                item.wind.speed.toString(), // wind speed
                item.clouds.all.toString() // cloudiness
            )

            dailyForecastList.add(forecastItem)

    }

    return dailyForecastList
}

