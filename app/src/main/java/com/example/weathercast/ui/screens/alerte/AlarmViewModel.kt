package com.example.weathercast.ui.screens.alerte

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlarmViewModel(val repo: Repository) : ViewModel() {

    private val _mutableAlarmListState = MutableStateFlow<Response>(Response.Loading)
    var alarmListState = _mutableAlarmListState.asStateFlow()

    private var weatherModelMutableStateFlow = MutableStateFlow<Response>(Response.Loading)
    var weatherModel = weatherModelMutableStateFlow.asStateFlow()


    fun insertAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAlarm(alarm)
        }
    }

    fun getAllAlarm() {
        Log.i("alarm", "getAllAlarm:1  ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = repo.getAllAlarm()
                response.catch {
                    _mutableAlarmListState.emit(Response.Error(it.message ?: "Error"))
                }.collect { it ->
                    Log.i("alarm", "getAllAlarm: 2  ")
                    for (item in it as MutableList<Alarm>) {
                        Log.i("alarm", "getAllAlarm: 3 ${item.dateTime} ")
                    }
                    _mutableAlarmListState.emit(Response.Success(it))
                }
            } catch (ex: Exception) {
                _mutableAlarmListState.emit(Response.Error(ex.message ?: "Error"))
            }
        }
    }

    //flag important
    fun getWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val language = repo.getLanguage()
                val tempUnit = repo.getTemperatureUnit()
                val latLong = repo.getGlobleLatLong()

                val response = repo.getWeather(
                    latLong.latitude,
                    latLong.longitude,
                    language ?: Constants.Emglish_PARM,
                    tempUnit ?: Constants.CELSIUS_PARM
                )
                response.collect { it ->
                    weatherModelMutableStateFlow.emit(Response.Success(it!!))
                }

            } catch (ex: Exception) {
                weatherModelMutableStateFlow.emit(Response.Error(ex.message.toString()))
            }
        }
    }
}

    class AlarmViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AlarmViewModel(repo) as T
        }
    }