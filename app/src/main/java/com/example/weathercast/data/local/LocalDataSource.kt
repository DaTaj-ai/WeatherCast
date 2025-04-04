package com.example.mvvm_lab3.data.localDataSource

import com.example.lab4workmanager.database.WeatherDao
import com.example.weathercast.data.local.AlarmDao
import com.example.weathercast.data.localDataSource.ILocalDataSource
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalDataSource(private val daoWeather: WeatherDao, private val alarmDao: AlarmDao) :
    ILocalDataSource {

    override suspend fun insertLocation(location: Location): Long {
        return daoWeather.insertLocation(location)
    }

    override suspend fun deleteLocation(location: Location): Int {
        return daoWeather.deleteLocation(location)
    }

    override suspend fun getAllLocations(): Flow<List<Location>> {
        return daoWeather.getAllLocations()
    }


    // weather part

    override fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?> {
        return daoWeather.getAllWeather().map { list ->
            list.firstOrNull { it.coord.lat == lat && it.coord.lon == lon }
        }
    }

    override fun insertWeather(weatherModel: WeatherModel): Long {
        return daoWeather.insertWeather(weatherModel)
    }

    override fun deleteWeather(weatherModel: WeatherModel): Int {
        return daoWeather.deleteWeather(weatherModel)
    }

    override fun getAllWeather(): Flow<List<WeatherModel>> {
        return daoWeather.getAllWeather()
    }

    override fun getWeatherById(id: Int): Flow<WeatherModel> {
        return daoWeather.getByid(id)
    }

    // Alarm
    override suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDao.insertAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: Alarm): Int {
        return alarmDao.deleteAlarm(alarm)
    }

    override fun getAllAlarm(): Flow<List<Alarm>> {
        return alarmDao.getAllAlarm()

    }
}