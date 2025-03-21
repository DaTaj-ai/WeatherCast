package com.example.weathercast.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercast.R
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.ui.screens.home.AirQualityItem
import com.example.weathercast.ui.theme.ColorAirQualityIconTitle
import com.example.weathercast.ui.theme.ColorSurface
import com.example.weathercast.ui.theme.ColorTextPrimary
import com.example.weathercast.ui.theme.ColorTextPrimaryVariant




@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AirQuality(
    modifier: Modifier = Modifier
    ,weather: WeatherModel?) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = ColorSurface
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 18.dp,
                horizontal = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var data: List<AirQualityItem> = listOf(
                AirQualityItem(
                    title = "Pressure",
                    value = weather!!.main.pressure.toString(),
                    icon = R.drawable.cloud_day_forecast_rain_rainy_icon
                ) ,AirQualityItem(
                title = "Humidity",
                value =  weather!!.main.humidity.toString() ,
                icon = R.drawable.cloud_day_forecast_rain_rainy_icon
                ),AirQualityItem(
                title = "Wind speed",
                value = weather!!.wind.speed.toString(),
                icon = R.drawable.cloud_day_forecast_rain_rainy_icon
                ),AirQualityItem(
                title = "clouds",
                value = weather!!.clouds.all.toString(),
                icon = R.drawable.cloud_day_forecast_rain_rainy_icon
                )
                )
                data.onEach { item ->
                    AirQualityInfo(
                        data = item,
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AirQualityHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.cloud_day_forecast_rain_rainy_icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = ColorAirQualityIconTitle
            )
            Text(
                text = "Air Quality",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp
                )
            )
        }

    }
}



@Composable
private fun AirQualityInfo(
    modifier: Modifier = Modifier,
    data: AirQualityItem
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            painter = painterResource(data.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = ColorAirQualityIconTitle
        )
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(fontSize = 25.sp,
                text = data.title,
                style = MaterialTheme.typography.labelMedium,
                color = ColorTextPrimaryVariant
            )
            Text(
                text = data.value,
                style = MaterialTheme.typography.labelMedium,
                color = ColorTextPrimary
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AirQualityPreview() {
    AirQuality(weather = null)
}