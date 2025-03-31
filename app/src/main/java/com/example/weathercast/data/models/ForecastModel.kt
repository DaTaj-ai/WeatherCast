package com.example.weathercast.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Entity(tableName = "forecast")
data class ForecastModel(
    @PrimaryKey
    var id: Int ,
    @TypeConverters(ForecastConverters::class)
    var city: City,
    var cnt: Int,
    var cod: String,
    @SerializedName("list")
    @TypeConverters(ForecastConverters::class)
    var forecastEntry: List<ForecastEntry>,
    var message: Int
)

// Convert a single ForecastEntry to JSON String
fun ForecastEntry.toJson(): String {
    return Gson().toJson(this)
}

// Convert JSON back to ForecastEntry
fun String.toForecastEntry(): ForecastEntry {
    return Gson().fromJson(this, ForecastEntry::class.java)
}

// For a List<ForecastEntry> (if needed)
fun List<ForecastEntry>.toJsonList(): String {
    return Gson().toJson(this)
}

fun String.toForecastEntryList(): List<ForecastEntry> {
    val type = object : TypeToken<List<ForecastEntry>>() {}.type
    return Gson().fromJson(this, type)
}

class ForecastConverters {
    private val gson = Gson()

    // City converter
    @TypeConverter
    fun cityToString(city: City): String = gson.toJson(city)

    @TypeConverter
    fun stringToCity(json: String): City = gson.fromJson(json, City::class.java)

    // List<ForecastEntry> converter
    @TypeConverter
    fun forecastEntriesToString(list: List<ForecastEntry>): String = gson.toJson(list)

    @TypeConverter
    fun stringToForecastEntries(json: String): List<ForecastEntry> {
        val type = object : TypeToken<List<ForecastEntry>>() {}.type
        return gson.fromJson(json, type)
    }

    // For other nested objects if needed
    @TypeConverter
    fun mainToString(main: Main): String = gson.toJson(main)

    @TypeConverter
    fun stringToMain(json: String): Main = gson.fromJson(json, Main::class.java)
}


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

