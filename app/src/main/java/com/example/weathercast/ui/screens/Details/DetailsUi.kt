package com.example.weathercast.ui.screens.Details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.weathercast.ui.screens.home.components.ForecastItem



@Composable
fun DetailsUi(forecastItem: ForecastItem) {
    Text("how are you from details ${forecastItem.dayOfWeek}" )
}