package com.example.weathercast.utlis

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getDate(dt:Int):String{
    //SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var date = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(Date(dt * 1000L))
    return date
}

