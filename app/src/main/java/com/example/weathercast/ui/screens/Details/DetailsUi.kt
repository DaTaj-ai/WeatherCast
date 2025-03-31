package com.example.weathercast.ui.screens.Details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.ui.screens.home.components.RealHomeScreen
import com.google.android.gms.maps.model.LatLng


@Composable
fun DetailsUi(detailsViewModel: DetailsViewModel, latLong: LatLng) {

    detailsViewModel.getWeather(latLong.latitude, latLong.longitude)

    val weather by detailsViewModel.weatherState.collectAsStateWithLifecycle()


    weather?.let {
        RealHomeScreen(it)
    }

}