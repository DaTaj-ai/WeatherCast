package com.example.weathercast.ui.screens.V2home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weathercast.R
import com.example.weathercast.ui.screens.home.components.ForecastItem
import com.example.weathercast.utlis.formatNumberBasedOnLanguage


@Composable
fun WeeklyForecastCard(
    weeklyForecastDate: List<ForecastItem>,
    onClickNavigate: (ForecastItem) -> Unit
) {
    Text(
        text = stringResource(R.string.weekly_forecast),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )

    Card(
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = cardElevation(defaultElevation = 12.dp)
    )
    {
        repeat(weeklyForecastDate.size) {

            WeeklyItem(weeklyForecastDate[it], { onClickNavigate(it) })
            if (it != 4) {
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
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
                .align(androidx.compose.ui.Alignment.CenterVertically)
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
            modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically)
        )
        Text(
            text = formatNumberBasedOnLanguage(data.temperature.toString()), modifier = Modifier
                .padding(end = 16.dp)
                .align(androidx.compose.ui.Alignment.CenterVertically)
        )

    }
}



