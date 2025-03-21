package com.example.weathercast.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.dailyForecasts
import com.example.weathercast.data.models.getTodayForecast
import com.example.weathercast.ui.screens.home.components.AirQuality
import com.example.weathercast.ui.screens.home.components.DailyForecast
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.Response


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel) {
    var context = LocalContext.current.applicationContext

    val weather by homeScreenViewModel.weatherModel.collectAsStateWithLifecycle()
    val forecast by homeScreenViewModel.forecastModel.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        try {
            val sp = context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE)
            val language = sp.getString(Constants.LANGUAGE, Constants.Emglish_PARM)
            val location = sp.getString(Constants.LOCATION, "GPS")
            val windSpeedUnit = sp.getString(Constants.WIND_SPEED_UNIT, "m/s")
            val tempUnit = sp.getString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM)

            Log.i("SP", "Shared prefrance what : ${language}  ")
            Log.i("SP", "Shared prefrance what : ${location}  ")
            Log.i("SP", "Shared prefrance what : ${windSpeedUnit}  ")
            Log.i("SP", "Shared prefrance what : ${tempUnit} ")

            homeScreenViewModel.getWeather(20.22, 88.22, "ar", Constants.CELSIUS_PARM)

            homeScreenViewModel.getForecast(20.22, 65.22, "ar", Constants.CELSIUS_PARM)

        } catch (e: Exception) {
            Log.i("TAG", "HomeScreen:+ ${e.message} ")
        }
    }

    Column {
        when (weather) {
            is Response.Loading -> {
                // call Loading Logic
                Text(text = ("loading"))
            }

            is Response.Success -> {
                var weather = (weather as Response.Success).data as WeatherModel
                ActionBar(location = weather.name)
                DailyForecast(weather = (weather))
                AirQuality(weather = (weather))
            }

            is Response.Error -> {
                Text(text = (weather as Response.Error).message)
            }
        }

        when (forecast) {
            is Response.Loading -> {
                Log.i("TAG", "HomeScreen: Loading ")
                // call Loading Logic
                Text(text = ("loading"))
            }

            is Response.Success -> {
                Log.i("TAG", "HomeScreen:  Success ")
                val forecastLocal = (forecast as Response.Success).data as ForecastModel
                Log.i("TAG", "HomeScreen: ${forecastLocal.forecastEntry.toString()}")

                TodayForecast(forecastLocal.getTodayForecast())

                var value = forecastLocal.dailyForecasts()

                WeeklyForecast(data = value)
            }

            is Response.Error -> {
                Text(text = ((forecast as Response.Error).message))
                Log.i("TAG", "HomeScreen:  Error")
            }

        }

    }

}


