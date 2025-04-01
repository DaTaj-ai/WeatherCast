package com.example.weathercast.utlis

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
fun convertToMillis(date: LocalDate, time: LocalTime): Long {
    val zoneId = java.time.ZoneId.systemDefault()
    return date.atTime(time).atZone(zoneId).toInstant().toEpochMilli()
}
