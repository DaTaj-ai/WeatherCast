package com.example.weathercast.ui.screens.home.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercast.R
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.utlis.formatNumberBasedOnLanguage
import com.example.weathercast.utlis.getDate
import com.example.weathercast.utlis.weatherIcons
import kotlin.math.roundToInt


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RealHomeScreen(weather: WeatherModel, tempUintSmbol: String) {
    val itemsList = listOf(
        WeatherItem(
            ImageVector.vectorResource(id = R.drawable.humidity_),
            formatNumberBasedOnLanguage(weather.main.humidity.toString()),
            stringResource(R.string.humidity)
        ),
        WeatherItem(
            Icons.Outlined.Air, formatNumberBasedOnLanguage(weather.wind.speed.toString()),
            stringResource(R.string.wind_speed)
        ),
        WeatherItem(
            ImageVector.vectorResource(id = R.drawable.pressure),
            formatNumberBasedOnLanguage(weather.main.pressure.toString()),
            stringResource(R.string.pressure)
        ),
        WeatherItem(
            Icons.Outlined.Cloud,
            formatNumberBasedOnLanguage(weather.clouds.all.toString()),
            stringResource(R.string.clouds)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp),
        shape = RoundedCornerShape(
            bottomEnd = 50.dp,
            bottomStart = 50.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                model = R.drawable.rain,
                contentDescription = "Background GIF",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(4f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(
                    modifier = Modifier.padding(top = 16.dp),
                    location = weather.name,
                    dateString = getDate(weather.dt)
                )

                Image(
                    painter = painterResource(
                        weatherIcons.get(weather.weather[0].icon) ?: R.drawable.day_clear
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(300.dp)
                        .padding(top = 100.dp)
                )

                Text(
                    text = "${
                        formatNumberBasedOnLanguage(
                            weather.main.temp.roundToInt().toString()
                        )
                    }${tempUintSmbol}",
                    letterSpacing = 0.sp,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Black,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    modifier = Modifier.padding(end = 16.dp)
                )

                Text(
                    text = formatNumberBasedOnLanguage(weather.weather[0].description),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White, // Changed to white
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 20.dp, top = 8.dp)
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    //flag
                    color = Color.White.copy(alpha = 0.1f),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 85.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                TodayStat(
                    modifier = Modifier.padding(bottom = 16.dp),
                    itemsList
                )
            }
        }
    }
}

@Composable
fun TodayStat(modifier: Modifier = Modifier, weatherList: List<WeatherItem>) {
    Box(
        modifier = modifier
    ) {

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(weatherList.size) {
                ItemStat(
                    icon = weatherList[it].icon,
                    value = weatherList[it].value,
                    type = weatherList[it].type
                )
            }
        }
    }
}

@Composable
fun ItemStat(icon: ImageVector, value: String, type: String) {
    Card(
        modifier = Modifier
            .width(89.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp), colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Text(text = type, style = MaterialTheme.typography.labelSmall, color = Color.White)
        }
    }
}

data class WeatherItem(
    val icon: ImageVector,
    val value: String,
    val type: String
)


