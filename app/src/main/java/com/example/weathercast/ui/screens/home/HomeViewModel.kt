package com.example.weathercast.ui.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class HomeScreenViewModel(private val repo: Repository) : ViewModel() {

    init {
//        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//
//        getWeather(44.34, 10.99)
//        getForecast(44.34, 10.99)
    }

    private var weatherModelMutableStateFlow = MutableStateFlow<Response>(Response.Loading)
    var weatherModel = weatherModelMutableStateFlow.asStateFlow()


    private var forecastModelMutableLiveData = MutableStateFlow<Response>(Response.Loading)
    var forecastModel = forecastModelMutableLiveData.asStateFlow()

    fun getWeather(lat: Double, lon: Double, lang: String  , unit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getWeather(lat, lon , lang=  lang , unit = unit)
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


    fun getForecast(lat: Double, lon: Double , lang: String , unit: String) {
        viewModelScope.launch {
            try {
                var response = repo.getForecast(lat, lon ,lang , unit )
                response.catch {
                    forecastModelMutableLiveData.emit(Response.Error(it.message.toString()))
                }.collect {
                    forecastModelMutableLiveData.emit(Response.Success(it!!))
                    Log.i("TAG", "getForecast${Response.Success(it!!)} ")
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