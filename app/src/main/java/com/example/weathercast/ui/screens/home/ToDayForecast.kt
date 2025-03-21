package com.example.weathercast.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weathercast.R
import com.example.weathercast.data.models.DailyForecast
import com.example.weathercast.data.models.ForecastEntry

@Composable
fun TodayForecast(data: List<DailyForecast>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Hourly Forecast")
        LazyRow() {
            items(data.size) {
                HourlyForecastItem(data[it])
            }
        }
    }
}


@Composable
private fun HourlyForecastItem(forecastEntry: DailyForecast) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = forecastEntry.date, color = androidx.compose.ui.graphics.Color.Black)
        Image(
            painter = painterResource(id = R.drawable.cloud_day_forecast_rain_rainy_icon),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
        Text(
            text = "${forecastEntry.temperature}°C",
            color = androidx.compose.ui.graphics.Color.Black
        )
    }

}
