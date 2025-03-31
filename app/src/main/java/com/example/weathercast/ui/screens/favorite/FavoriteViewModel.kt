package com.example.weathercast.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    val _mutableStateLocationList = MutableStateFlow<List<Location>>(emptyList())
    val locations = _mutableStateLocationList.asStateFlow()


    val _mutableStateWeatherList = MutableStateFlow<List<WeatherModel>>(emptyList())
    val weatherList = _mutableStateWeatherList.asStateFlow()


    private fun insertWeather(weather: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteWeather(weather)
        }
    }

    fun deleteWeather(weather: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteWeather(weather)
        }
    }

    fun getAllFavoriteLocation(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoriteWeather().collect {
                _mutableStateWeatherList.emit(it)
            }
        }
    }

    // Remote part
    fun insertWeatherByLatLong(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            var lang = repository.getLanguage()
            var unit = repository.getTemperatureUnit()

            repository.getWeather(lat = lat, lon = lon , lang = lang?:"en" , unit = unit?: Constants.CELSIUS_PARM).collect {
                //flag
                insertWeather(it!!)
            }
        }
    }

    fun setGlobleLatLong(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setGlobleLatLong(lat.toString(), lon.toString())
        }
    }

//    fun getWeatherByLatLong(lat: Double, lon: Double) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getLocalWeatherByLatLong(lat, lon).collect {
//
//            }
//        }
//    }

}


class FavoritesScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repo) as T
    }
}