package com.example.weathercast.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.weathercast.R
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.dailyForecasts
import com.example.weathercast.data.models.getTodayForecast
import com.example.weathercast.data.models.toJsonObject
import com.example.weathercast.ui.screens.home.components.RealHomeScreen
import com.example.weathercast.ui.screens.home.components.TodayForecast
import com.example.weathercast.ui.screens.home.components.WeeklyForecastCard
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.NetworkState
import com.example.weathercast.utlis.Response


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel, navController: NavHostController) {
    var context = LocalContext.current.applicationContext
    //val network by homeScreenViewModel.netWorkManger.collectAsStateWithLifecycle()

    homeScreenViewModel.observeNetworkChanges(context)

    LaunchedEffect(Unit) {
        val networkState = NetworkState(context)
        val isOnline = networkState.isNetworkAvailable()
        homeScreenViewModel.getWeatherDataCall(
            isOnline
        )
    }

    val weather by homeScreenViewModel.weatherModel.collectAsStateWithLifecycle()
    val forecast by homeScreenViewModel.forecastModel.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        homeScreenViewModel.networkEvents.collect { event ->
            Log.i("offline", "HomeScreen: ${event} ")
            when (event) {
                NetworkEvent.Connected -> {
                    Toast.makeText(context, "Back online", Toast.LENGTH_SHORT).show()

                    val language = homeScreenViewModel.getLanguage()
                    val windSpeedUnit = homeScreenViewModel.getWindSpeed()
                    val tempUnit = homeScreenViewModel.getTemperatureUnit()
                    val latLong = homeScreenViewModel.getGlobleLatLong()

                    homeScreenViewModel.getWeather(
                        latLong.latitude,
                        latLong.longitude,
                        language ?: Constants.Emglish_PARM,
                        tempUnit ?: Constants.CELSIUS_PARM
                    )
                    homeScreenViewModel.getForecastOnline(
                        latLong.latitude,
                        latLong.longitude,
                        language ?: Constants.Emglish_PARM,
                        tempUnit ?: Constants.CELSIUS_PARM
                    )

                    showDialog = false
                }

                NetworkEvent.Disconnected -> {
                    Log.i("TAG", "HomeScreen: here offline")
//                    homeScreenViewModel.getWeatherOffline()
                    showDialog = true
                }
            }

        }
    }



    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        when (weather) {
            is Response.Loading -> {
                // call Loading Logic
                Text(text = stringResource(id = R.string.loading))
            }

            is Response.Success -> {
                var weather = (weather as Response.Success).data as WeatherModel
                RealHomeScreen(weather)
            }

            is Response.Error -> {
                Text(text = (weather as Response.Error).message)
            }
        }

        when (forecast) {
            is Response.Loading -> {
                Text(text = stringResource(R.string.loading))
            }

            is Response.Success -> {

                val forecastLocal = (forecast as Response.Success).data as ForecastModel
                TodayForecast(forecastLocal.getTodayForecast())

                var value = forecastLocal.dailyForecasts()

                WeeklyForecastCard(value, { forecastItem ->
                    navController.navigate("details_screen/${forecastItem.toJsonObject()}")
                })
            }

            is Response.Error -> {
                Text(text = ((forecast as Response.Error).message))
            }
        }
    }



    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("No Internet") },
            text = { Text("Please check your connection") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

}


