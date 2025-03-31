package com.example.mvvm_lab3.data.localDataSource

import com.example.lab4workmanager.database.WeatherDao
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalDataSource(private val dao: WeatherDao) {


    suspend fun insertLocation(location: Location): Long {
        return dao.insertLocation(location)
    }

    suspend fun deleteLocation(location: Location): Int {
        return dao.deleteLocation(location)
    }

    suspend fun getAllLocations(): Flow<List<Location>> {
        return dao.getAllLocations()
    }


    // weather part

    fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?> {
        return dao.getAllWeather().map { list ->
            list.firstOrNull { it.coord.lat == lat && it.coord.lon == lon }
        }
    }

    suspend fun insertWeather(weatherModel: WeatherModel): Long {
        return dao.insertWeather(weatherModel)
    }

    suspend fun deleteWeather(weatherModel: WeatherModel): Int {
        return dao.deleteWeather(weatherModel)
    }

    suspend fun getAllWeather(): Flow<List<WeatherModel>> {
        return dao.getAllWeather()
    }

    suspend fun getWeatherById(id :Int): Flow<WeatherModel>{
        return dao.getByid(id)
    }

}