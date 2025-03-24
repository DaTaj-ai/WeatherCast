package com.example.weathercast.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.dailyForecasts
import com.example.weathercast.data.models.getTodayForecast
import com.example.weathercast.ui.screens.V2home.RealHomeScreen
import com.example.weathercast.ui.screens.V2home.WeeklyForecastCard
import com.example.weathercast.ui.screens.home.components.AirQuality
import com.example.weathercast.ui.screens.home.components.DailyForecast
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.Response
import java.io.IOException
import java.util.Locale


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
            val lat = sp.getString(Constants.USER_LAT, "0.0")?.toDouble()?:2.54
            val long = sp.getString(Constants.USER_LONG, "0.0")?.toDouble()?:2.54

            Log.i("SP", "Shared prefrance what : ${language}  ")
            Log.i("SP", "Shared prefrance what : ${location}  ")
            Log.i("SP", "Shared prefrance what : ${windSpeedUnit}  ")
            Log.i("SP", "Shared prefrance what tteemmp  : ${tempUnit} ")

            homeScreenViewModel.getWeather(lat, long,language?:Constants.Emglish_PARM, tempUnit?:Constants.CELSIUS_PARM)
            homeScreenViewModel.getForecast(lat, long,  language?:Constants.Emglish_PARM, tempUnit?:Constants.CELSIUS_PARM)
            homeScreenViewModel.getLocationName(context)

        } catch (e: Exception) {
            Log.i("TAG", "HomeScreen:+ ${e.message} ")
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


        when (weather) {
            is Response.Loading -> {
                // call Loading Logic
                Text(text = ("loading"))
            }

            is Response.Success -> {
                var weather = (weather as Response.Success).data as WeatherModel
                RealHomeScreen(weather)

//                Header(location = weather.name)
//                DailyForecast(weather = (weather))
//                AirQuality(weather = (weather))
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
                WeeklyForecastCard(value)
//
//                WeeklyForecast(data = value)
            }

            is Response.Error -> {
                Text(text = ((forecast as Response.Error).message))
                Log.i("TAG", "HomeScreen:  Error")
            }

        }
    }


}


