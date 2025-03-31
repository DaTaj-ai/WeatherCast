package com.example.weathercast.ui.screens.home

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.models.City
import com.example.weathercast.data.models.Coord
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.NetworkMonitor
import com.example.weathercast.utlis.Response
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale


class HomeScreenViewModel(private val repo: Repository) : ViewModel() {

    private val _networkEvents = MutableSharedFlow<NetworkEvent>(replay = 0)
    val networkEvents = _networkEvents.asSharedFlow()


    fun getWeatherDataCall(isOnline: Boolean) {
        val language = repo.getLanguage()
        val windSpeedUnit = repo.getWindSpeed()
        val tempUnit = repo.getTemperatureUnit()
        val latLong = repo.getGlobleLatLong()

        if (isOnline) {

            Log.i("SP", "Shared prefrance what : ${language}  ")
            Log.i("SP", "Shared prefrance what : ${windSpeedUnit}  ")
            Log.i("SP", "Shared prefrance what tteemmp  : ${tempUnit} ")

            getWeather(
                latLong.latitude,
                latLong.longitude,
                language ?: Constants.Emglish_PARM,
                tempUnit ?: Constants.CELSIUS_PARM
            )

            getForecastOnline(
                latLong.latitude,
                latLong.longitude,
                language ?: Constants.Emglish_PARM,
                tempUnit ?: Constants.CELSIUS_PARM
            )
        } else {
            getWeatherOffline()
            getForecastLocal()
        }
    }

    private var networkMonitor: NetworkMonitor? = null

    fun observeNetworkChanges(context: Context) {
        if (networkMonitor == null) {
            networkMonitor = NetworkMonitor(context)
            viewModelScope.launch {
                networkMonitor?.networkStatus?.collect { isConnected ->
                    _networkEvents.emit(
                        if (isConnected) NetworkEvent.Connected
                        else NetworkEvent.Disconnected
                    )
                }
            }
        }
    }


    private var weatherModelMutableStateFlow = MutableStateFlow<Response>(Response.Loading)
    var weatherModel = weatherModelMutableStateFlow.asStateFlow()


    private var forecastModelMutableLiveData = MutableStateFlow<Response>(Response.Loading)
    var forecastModel = forecastModelMutableLiveData.asStateFlow()

    private var locationNameStateMutable = MutableStateFlow<Response>(Response.Loading)
    var locationNameState = forecastModelMutableLiveData.asStateFlow()

    var netWorkManger = MutableStateFlow<Boolean>(false)


    fun getWeather(lat: Double, lon: Double, lang: String, unit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getWeather(lat, lon, lang = lang, unit = unit)
                response.catch {
                    weatherModelMutableStateFlow.emit(Response.Error(it.message.toString()))
                }.collect {
                    weatherModelMutableStateFlow.emit(Response.Success(it!!))
                    repo.insertFavoriteWeather(it!!)
                    repo.setCurrentWeatherId(it.id)
                }
            } catch (ex: Exception) {
                weatherModelMutableStateFlow.emit(Response.Error(ex.message.toString()))
            }
        }

    }

    private fun getForecastLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("forecast", "getLocalForecast:${repo.getLocalForecast()}")
            var forecast = ForecastModel(
                12,
                City(Coord(11.11, 11.11), "11.11", 12, "dds", 12, 12, 12, 12),
                12,
                "No cod",
                repo.getLocalForecast(),
                12
            )
            forecastModelMutableLiveData.emit(Response.Success(forecast))
        }
    }


    fun getForecastOnline(lat: Double, lon: Double, lang: String, unit: String) {
        viewModelScope.launch {
            try {
                var response = repo.getForecast(lat, lon, lang, unit)

                response.catch {
                    forecastModelMutableLiveData.emit(Response.Error(it.message.toString()))
                }.collect {
                    it?.let { forecastModel -> repo.setForecast(forecastModel.forecastEntry) }
                    forecastModelMutableLiveData.emit(Response.Success(it!!))
                    Log.i("TAG", "getForecast${Response.Success(it!!)} ")
                }
            } catch (ex: Exception) {
                forecastModelMutableLiveData.emit(Response.Error(ex.message.toString()))
            }
        }
    }


    fun getWeatherOffline() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var id = repo.getCurrentWeatherId()
                var response = repo.getWeatherById(id)
                response.catch {
                    Log.i("offline", "getWeatherOffline:  error ")
                    weatherModelMutableStateFlow.emit(Response.Error(it.message.toString()))
                }.collect {
                    Log.i("offline", "getWeatherOffline: ${it}")
                    Log.i("offline", "getWeatherOffline:${it?.name ?: "no data"} ")
                    weatherModelMutableStateFlow.emit(Response.Success(it!!))
                }
            } catch (ex: Exception) {
                weatherModelMutableStateFlow.emit(Response.Error(ex.message.toString()))
//                31.2395768  , 29.9702279
//                {"lat":31.2396,"lon":29.9702}
            }
        }
    }

    //    {"lat":31.2396,"lon":29.9702}
    fun upsertWeather(weatherModel: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertFavoriteWeather(weatherModel)
        }
    }

    fun getLocationName(context: Context) {
        var sp = context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE)
        val lat = sp.getString(Constants.USER_LAT, "0.0")?.toDouble() ?: 2.54
        val long = sp.getString(Constants.USER_LONG, "0.0")?.toDouble() ?: 2.54

        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            Log.i("TAG", "main : ${lat}")
            Log.i("TAG", " lat and long${long}")
            val addresses: List<Address>? = geocoder.getFromLocation(lat, long, 1)
            addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown"
            Log.i("TAG", "getLocationName: ${addresses?.firstOrNull()?.getAddressLine(0)} ")
        } catch (e: IOException) {
            e.printStackTrace()
            "Unknown"
        }
    }


    fun getLanguage(): String? {
        return repo.getLanguage()
    }

    fun getTemperatureUnit(): String? {
        return repo.getTemperatureUnit()
    }

    fun getLocationType(): String? {
        return repo.getLocationType()
    }

    fun getWindSpeed(): String? {
        return repo.getWindSpeed()
    }

    fun getGlobleLatLong(): LatLng {
        return repo.getGlobleLatLong()
    }


}


sealed class NetworkEvent {
    object Connected : NetworkEvent()
    object Disconnected : NetworkEvent()
}


class HomeScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(repo) as T
    }
}