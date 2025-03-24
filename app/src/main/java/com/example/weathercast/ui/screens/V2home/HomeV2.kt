package com.example.weathercast.ui.screens.V2home


import android.app.ActionBar
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercast.R
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.ui.screens.home.Header
import com.example.weathercast.ui.screens.home.WeeklyForecast
import com.example.weathercast.ui.theme.ColorTextSecondary
import com.example.weathercast.utlis.getDate



@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RealHomeScreen(weather: WeatherModel) {
    val itemsList = listOf(
        WeatherItem(ImageVector.vectorResource(id = R.drawable.humidity_), weather.main.humidity.toString(), "Humidity"),
        WeatherItem(Icons.Outlined.Air, weather.wind.speed.toString(), "Wind speed"),
        WeatherItem(ImageVector.vectorResource(id = R.drawable.pressure), weather.main.pressure.toString(), "Pressure"),
        WeatherItem(Icons.Outlined.Cloud, weather.clouds.all.toString(), "Clouds")
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
        // This Box will contain both the background and foreground content
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Background Image (placed first so it's at the bottom)
            GlideImage(
                model = R.drawable.rain,
                contentDescription = "Background GIF",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(4f), // Reduce opacity so content is readable
                contentScale = ContentScale.Crop
            )

            // 2. Foreground Content
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(location = weather.name, dateString = getDate(weather.dt))

                Image(
                    painter = painterResource(R.drawable.cloud_day_forecast_rain_rainy_icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(300.dp)
                        .padding(top = 100.dp)
                )

                Text(
                    text = "${weather.main.temp}°",
                    letterSpacing = 0.sp,
                    style = TextStyle(
                        color = Color.White, // Changed to white for better visibility
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
                    text = weather.weather[0].description,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White, // Changed to white
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 20.dp, top = 8.dp)
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.White.copy(alpha = 0.5f)
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

//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//fun RealHomeScreen(weather:WeatherModel) {
//    val itemsList = listOf(
//        WeatherItem( ImageVector.vectorResource(id = R.drawable.humidity_)  , weather.main.humidity.toString(), "Humidity"),
//        WeatherItem(Icons.Outlined.Air, weather.wind.speed.toString(), "Wind speed"),
//        WeatherItem(ImageVector.vectorResource(id = R.drawable.pressure) , weather.main.pressure.toString(), "Pressure"),
//        WeatherItem(Icons.Outlined.Cloud, weather.clouds.all.toString(), "Clouds")
//    )
//
//
//    // current  Hour and Minute
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(700.dp),
//        shape = RoundedCornerShape(
//            bottomEnd = 50.dp,
//            bottomStart = 50.dp
//        ), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Correct elevation
//    ) {
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.LightGray),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            Column {
//                Header(location = weather.name  , dateString = getDate(weather.dt))
//
//
//                Image(
//                    painter = painterResource(R.drawable.cloud_day_forecast_rain_rainy_icon),
//                    contentDescription = null,
//                    contentScale = ContentScale.FillHeight,
//                    modifier = Modifier
//                        .height(300.dp)
//                        .padding(top = 100.dp)
//                )
//                Text(
//                    text = weather.main.temp.toString(),
//                    letterSpacing = 0.sp,
//                    style = TextStyle(
//                        brush = Brush.verticalGradient(
//                            0f to Color.White,
//                            1f to Color.White.copy(alpha = 0.3f)
//                        ),
//                        fontSize = 80.sp,
//                        fontWeight = FontWeight.Black
//                    ),
//                    modifier = Modifier.padding(end = 16.dp)
//                )
//                Text(
//                    text = weather.weather.get(0).description,
//                    style = MaterialTheme.typography.titleLarge,
//                    color = ColorTextSecondary,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(start = 20.dp, top = 8.dp)
//                )
//                HorizontalDivider(thickness = 2.dp)
//
//            }
//
//                TodayStat(
//                    modifier = Modifier.align(Alignment.BottomCenter)
//                        .padding(bottom = 16.dp)
//                 ,itemsList)
//
//
//
//            GlideImage(
//                model = R.drawable.rain ,
//                contentDescription = "Background GIF",
//                modifier = Modifier.fillMaxSize().alpha(4f),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}

@Composable
fun TodayStat(modifier: Modifier = Modifier , weatherList:List<WeatherItem> ) {
    Box(
        modifier = modifier
    ) {

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(weatherList.size) {
                ItemStat(icon = weatherList[it].icon, value = weatherList[it].value, type = weatherList[it].type)
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
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
            Text(text = type, style = MaterialTheme.typography.labelSmall)
        }
    }
}

data class WeatherItem(
    val icon: ImageVector,
    val value: String,
    val type: String
)



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeTest() {
//    RealHomeScreen( )
//}


//
//
//@Composable
//fun ActionBar(
//    modifier: Modifier = Modifier , location: String
//) {
//    Row(
//        modifier = modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.Top,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        LocationInfo(
//            modifier = Modifier.padding(top = 10.dp),
//            location = location
//        )
//
//    }
//}
//
//
//@Composable
//private fun LocationInfo(
//    modifier: Modifier = Modifier,
//    location: String
//) {
//    Column(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            androidx.compose.material3.Icon(Icons.Filled.LocationOn, contentDescription = "")
//            Text(
//                text = location,
//                style = MaterialTheme.typography.titleLarge,
//                color = Color(0,0,0),
//                fontWeight = FontWeight.Bold,
//            )
//        }
//    }
//}
//
//@Composable
//private fun ProgressBar(
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .background(
//                brush = Brush.linearGradient(
//                    0f to ColorGradient1,
//                    0.25f to ColorGradient2,
//                    1f to ColorGradient3
//                ),
//                shape = RoundedCornerShape(8.dp)
//            )
//            .padding(
//                vertical = 2.dp,
//                horizontal = 10.dp
//            )
//    ) {
//        Text(
//            text = "Updating •",
//            style = MaterialTheme.typography.labelSmall,
//            color = ColorTextSecondary.copy(alpha = 0.7f)
//        )
//    }
//}


//@Composable
//@Preview(showBackground = true)
//private fun CardBackground(
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(
//                brush = Brush.linearGradient(
//                    0f to ColorGradient1,
//                    0.5f to ColorGradient2,
//                    1f to ColorGradient3
//                ),
//                shape = RoundedCornerShape(32.dp)
//            )
//    )
//}
//@Composable
//private fun ControlButton(
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        color = ColorSurface,
//        shape = CircleShape,
//        modifier = modifier
//            .size(48.dp)
//            .customShadow(
//                color = Color.Black,
//                alpha = 0.15f,
//                shadowRadius = 16.dp,
//                borderRadius = 48.dp,
//                offsetY = 4.dp
//            ),
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                contentDescription = null,
//                modifier = Modifier.size(20.dp)
//            )
//        }
//    }
//}
//
//@Composable
//private fun ProfileButton(
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = Modifier
//            .size(48.dp)
//            .border(
//                width = 1.5.dp,
//                color = ColorSurface,
//                shape = CircleShape
//            )
//            .customShadow(
//                color = ColorImageShadow,
//                alpha = 0.7f,
//                shadowRadius = 12.dp,
//                borderRadius = 48.dp,
//                offsetY = 6.dp
//            )
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = null,
//            modifier = modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//        )
//    }
//}
