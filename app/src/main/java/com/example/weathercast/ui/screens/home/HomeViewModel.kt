package com.example.weathercast.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.data.repository.Repository


class HomeScreenViewModel(private val repo: Repository) : ViewModel() {

    suspend fun getWeather(lat: Double, lon: Double) {
        repo.getWeather(lat, lon)
    }
    suspend fun getForecast(lat: Double, lon: Double) {
        repo.getForecast(lat, lon)
    }
}

class HomeScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(repo) as T
    }
}