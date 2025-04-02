package com.example.weathercast.ui.screens.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weathercast.R
import com.example.weathercast.data.models.DailyForecast
import com.example.weathercast.ui.theme.MyDarkBlue
import com.example.weathercast.ui.theme.MyLightBlue
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.formatNumberBasedOnLanguage
import com.example.weathercast.utlis.getTempUnitSymbol
import com.example.weathercast.utlis.weatherIcons

@Composable
fun TodayForecast(data: List<DailyForecast> , tempUint:String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.today_forecast), style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow() {
            items(data.size) {
                HourlyForecastItem(data[it] , getTempUnitSymbol(tempUint))
            }
        }
    }
}


@Composable
private fun HourlyForecastItem(forecastEntry: DailyForecast ,tempUint:String) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            ,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MyDarkBlue,  // Dark blue
                            MyLightBlue   // Light blue
                        )
                    )
                )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${formatNumberBasedOnLanguage(forecastEntry.temperature)}${tempUint} " ,

                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Image(
                    painter = painterResource(
                        weatherIcons.get(forecastEntry.icon) ?: R.drawable.day_clear
                    ),
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .padding(top = 10.dp)
                )

                Text(
                    text = forecastEntry.date,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}