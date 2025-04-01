package com.example.weathercast.ui.screens.alerte

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.ui.screens.home.HomeScreenViewModel
import com.example.weathercast.utlis.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlarmViewModel(val repo: Repository) : ViewModel() {

    private val _mutableAlarmListState = MutableStateFlow<Response>(Response.Loading)
    var alarmListState = _mutableAlarmListState.asStateFlow()

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
                    _mutableAlarmListState.emit(Response.Error(it.message?:"Error"))
                }.collect { it ->
                    Log.i("alarm", "getAllAlarm: 2  ")
                    for(item in it as MutableList<Alarm>){
                        Log.i("alarm", "getAllAlarm: 3 ${item.dateTime} ")
                    }
                    _mutableAlarmListState.emit(Response.Success(it))
                }
            } catch (ex: Exception) {
                _mutableAlarmListState.emit(Response.Error(ex.message?:"Error"))
            }
        }
    }
}


class AlarmViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(repo) as T
    }
}