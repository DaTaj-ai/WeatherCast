package com.example.weathercast.ui.screens.Details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class DetailsViewModel(var repo: Repository) : ViewModel() {

    var lang = repo.getLanguage()
    var unit = repo.getTemperatureUnit()

    private var _mutableWeatherState = MutableStateFlow<WeatherModel?>(null)
    var weatherState = _mutableWeatherState


    fun getWeather(lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getWeather(
                lat,
                long,
                lang ?: Constants.Emglish_PARM,
                unit ?: Constants.FAHRENHEIT_PARM
            ).collect {
                _mutableWeatherState.emit(it)
            Log.i("TAG", "getWeather: xdflhlkfjhlfkdjhfdlkjhdfl${it?.weather} ")
            }
        }

    }

}

class DetailsScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(repo) as T
    }
}