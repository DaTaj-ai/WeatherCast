package com.example.weathercast.data.models

import android.net.Uri
import com.google.gson.Gson
import kotlinx.serialization.Serializable

@Serializable
data class ForecastItem(
    val dayOfWeek: String,
    val date: String,
    val temperature: String,
    val description: String,
    val icon: String,
    val isSelected: Boolean = false,

)
fun ForecastItem.toJsonObject() = Uri.encode(Gson().toJson(this))


