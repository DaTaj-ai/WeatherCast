package com.example.mvvm_lab3.data.localDataSource

import com.example.lab4workmanager.database.WeatherDao
import com.example.weathercast.data.local.AlarmDao
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalDataSource(private val daoWeather: WeatherDao, private val alarmDao: AlarmDao) {


    suspend fun insertLocation(location: Location): Long {
        return daoWeather.insertLocation(location)
    }

    suspend fun deleteLocation(location: Location): Int {
        return daoWeather.deleteLocation(location)
    }

    suspend fun getAllLocations(): Flow<List<Location>> {
        return daoWeather.getAllLocations()
    }


    // weather part

    fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?> {
        return daoWeather.getAllWeather().map { list ->
            list.firstOrNull { it.coord.lat == lat && it.coord.lon == lon }
        }
    }

    fun insertWeather(weatherModel: WeatherModel): Long {
        return daoWeather.insertWeather(weatherModel)
    }

    fun deleteWeather(weatherModel: WeatherModel): Int {
        return daoWeather.deleteWeather(weatherModel)
    }

    fun getAllWeather(): Flow<List<WeatherModel>> {
        return daoWeather.getAllWeather()
    }

    fun getWeatherById(id: Int): Flow<WeatherModel> {
        return daoWeather.getByid(id)
    }

    // Alarm
    suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDao.insertAlarm(alarm)
    }

    suspend fun deleteAlarm(alarm: Alarm): Int {
        return alarmDao.deleteAlarm(alarm)
    }

    fun getAllAlarm(): Flow<List<Alarm>> {
        return alarmDao.getAllAlarm()

    }
}