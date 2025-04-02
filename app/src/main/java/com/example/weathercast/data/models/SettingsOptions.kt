package com.example.weathercast.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingOption(
    val title: String,
    val options: List<String>,
    val onSelection: (String) -> Unit ,
    val icon : ImageVector
)

