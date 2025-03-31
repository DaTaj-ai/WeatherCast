package com.example.weathercast.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weathercast.R
import com.example.weathercast.data.models.ForecastItem
import com.example.weathercast.utlis.formatNumberBasedOnLanguage


@Composable
fun WeeklyForecastCard(
    weeklyForecastDate: List<ForecastItem>,
    onClickNavigate: (ForecastItem) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.weekly_forecast),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Card(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp , bottom = 95.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF3A7BD5),  // Dark blue
                                Color(0xFF00D2FF)   // Light blue
                            )
                        )
                    )
            ) {
                Column {
                    weeklyForecastDate.take(5).forEachIndexed { index, forecastItem ->
                        WeeklyItem(forecastItem) { onClickNavigate(forecastItem) }
                        if (index != weeklyForecastDate.lastIndex.coerceAtMost(4)) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Color.White.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun WeeklyItem(data: ForecastItem, onClick: (ForecastItem) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(data) }
            .padding(16.dp),
    ) {
        Text(
            text = "${data.dayOfWeek} ${data.date}", modifier = Modifier
                .padding(start = 16.dp)
                .align(androidx.compose.ui.Alignment.CenterVertically), color = Color.White

        )
        Image(
            painter = painterResource(id = R.drawable.cloud_day_forecast_rain_rainy_icon),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(top = 4.dp)
                .align(androidx.compose.ui.Alignment.CenterVertically)
        )
        Text(
            text = data.description,
            modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically),
            color = Color.White
        )
        Text(
            text = formatNumberBasedOnLanguage(data.temperature.toString()), modifier = Modifier
                .padding(end = 16.dp)
                .align(androidx.compose.ui.Alignment.CenterVertically), color = Color.White
        )

    }
}



