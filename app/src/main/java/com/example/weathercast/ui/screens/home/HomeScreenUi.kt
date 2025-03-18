package com.example.weathercast.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.ui.screens.home.components.AirQuality
import com.example.weathercast.ui.screens.home.components.DailyForecast
import com.example.weathercast.utlis.Response


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel) {

    val weather = homeScreenViewModel.weatherModel.collectAsStateWithLifecycle()
    val forecast = homeScreenViewModel.forecastModel.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        try {
            homeScreenViewModel.getWeather(44.34, 10.99)
            //homeScreenViewModel.getForecast(44.34, 10.99)
        } catch (e: Exception) {
            Log.i("TAG", "HomeScreen:+ ${e.message} ")
        }
    }

    when (weather.value) {
        is Response.Loading -> {
            // call Loading Logic
            Text(text = ("loading"))
        }

        is Response.Success -> {
            var weather = (weather.value as Response.Success).data as WeatherModel
            Column {
                ActionBar(location= weather.name)
                DailyForecast(weather = (weather))
                    AirQuality(weather = (weather))
//                    WeeklyForecast()

            }
        }

        is Response.Error -> {
            Text(text = (weather.value as Response.Error).message)
        }

    }

//    when (forecast.value) {
//        is Response.Loading -> {
//            // call Loading Logic
//            Text(text = ("loading"))
//        }
//
//        is Response.Success -> {
//            Column {
//                DailyForecast(weather = (weather.value as Response.Success).data as WeatherModel)
////                    AirQuality()
////                    WeeklyForecast()
//            }
//        }
//
//        is Response.Error -> {
//            Text(text = (weather.value as Response.Error).message)
//        }
//
//    }

}


