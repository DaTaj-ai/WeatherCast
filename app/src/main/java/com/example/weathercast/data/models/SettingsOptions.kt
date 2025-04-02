package com.example.weathercast.data.models
data class SettingOption(
    val title: String,
    val options: List<String>,
    val onSelection: (String) -> Unit
)

