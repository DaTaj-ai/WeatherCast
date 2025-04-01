package com.example.weathercast.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlarmTable")
class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 ,
    var dateTime: Long
)
fun Alarm.getFormattedTime(): String {
    val date = java.util.Date(dateTime)
    val format = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    return format.format(date)
}

fun Alarm.getFormattedDate(): String {
    val date = java.util.Date(dateTime)
    val format = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
    return format.format(date)
}
