package com.example.weathercast.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class HomeScreenViewModel(private val repo: Repository) : ViewModel() {

    private var weatherModelMutableStateFlow = MutableStateFlow<Response>(Response.Loading)
    var weatherModel = weatherModelMutableStateFlow


    private var forecastModelMutableLiveData = MutableStateFlow<Response>(Response.Loading)
    var forecastModel = forecastModelMutableLiveData

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getWeather(lat, lon)
                response.catch {
                    weatherModelMutableStateFlow.emit(Response.Error(it.message.toString()))
                }.collect {
                    weatherModelMutableStateFlow.emit(Response.Success(it!!))
                }
            } catch (ex: Exception) {
                weatherModelMutableStateFlow.emit(Response.Error(ex.message.toString()))
            }
        }
    }


    fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                var response = repo.getForecast(lat, lon)
                response.catch {
                    forecastModelMutableLiveData.emit(Response.Error(it.message.toString()))
                }.collect {
                    forecastModelMutableLiveData.emit(Response.Success(it!!))
                }
            } catch (ex: Exception) {
                forecastModelMutableLiveData.emit(Response.Error(ex.message.toString()))
            }
        }
    }
}

class HomeScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(repo) as T
    }
}