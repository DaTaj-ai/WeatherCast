package com.example.weathercast.ui.screens.home.components

import android.net.Uri
import androidx.annotation.DrawableRes
import com.example.weathercast.R
import com.google.gson.Gson
import kotlinx.serialization.Serializable

@Serializable
data class ForecastItem(
    val dayOfWeek: String,
    val date: String,
    val temperature: String,
    val description: String,
    val icon: String,
    val isSelected: Boolean = false
)
fun ForecastItem.toJsonObject() = Uri.encode(Gson().toJson(this))


