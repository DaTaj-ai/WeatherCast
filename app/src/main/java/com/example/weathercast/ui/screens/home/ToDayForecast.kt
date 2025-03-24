package com.example.weathercast.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weathercast.R
import com.example.weathercast.data.models.DailyForecast

@Composable
fun TodayForecast(data: List<DailyForecast>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Hourly Forecast", style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow() {
            items(data.size) {
                HourlyForecastItem(data[it])
            }
        }
    }
}


@Composable
private fun HourlyForecastItem(forecastEntry: DailyForecast) {

    Card(
        modifier = Modifier
            .padding(8.dp)/*.width(120.dp).height(150.dp)*/, elevation = cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(Color(0xff1680f5))

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "${forecastEntry.temperature}°C",
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.cloud_day_forecast_rain_rainy_icon),
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .padding(top = 10.dp)
            )

            Text(text = forecastEntry.date  , color = Color.White)
        }

    }
}