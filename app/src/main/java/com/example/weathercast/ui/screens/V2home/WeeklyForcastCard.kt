package com.example.weathercast.ui.screens.V2home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.impl.utils.ForceStopRunnable
import com.example.weathercast.R
import com.example.weathercast.data.models.ForecastEntry
import com.example.weathercast.ui.screens.home.components.ForecastItem


@Composable
fun WeeklyForecastCard(weeklyForecastDate: List<ForecastItem> ) {
    Text(
        text = "Weekly Forecast", style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )

    Card(
        modifier = Modifier.padding(16.dp , 16.dp , 16.dp , 100.dp) ,
        shape = RoundedCornerShape(16.dp),
        elevation = cardElevation(defaultElevation = 12.dp)
    )
    {

        repeat(weeklyForecastDate.size) {
            Item(weeklyForecastDate[it])
            if (it != 4) {
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}


//@Preview
//@Composable
//private fun WeeklyForecastCardPreview() {
//    WeeklyForecastCard()
//}


@Composable
private fun Item(data: ForecastItem ) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        ) {
        Text(text = "${data.dayOfWeek} ${data.date}" , modifier = Modifier.padding(start = 16.dp).align(androidx.compose.ui.Alignment.CenterVertically)  )
        Image(
            painter = painterResource(id = R.drawable.cloud_day_forecast_rain_rainy_icon),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(top = 4.dp).align(androidx.compose.ui.Alignment.CenterVertically)
        )
        Text(text = data.description , modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically) )
        Text(text = data.temperature , modifier = Modifier.padding(end = 16.dp).align(androidx.compose.ui.Alignment.CenterVertically) )

    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ItemPreview() {
//    Item()
//}




