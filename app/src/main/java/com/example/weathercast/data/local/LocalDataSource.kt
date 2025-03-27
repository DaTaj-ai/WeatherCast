package com.example.mvvm_lab3.data.localDataSource

import com.example.lab4workmanager.database.WeatherDao
import com.example.weathercast.data.models.Location
import kotlinx.coroutines.flow.Flow


class LocalDataSource(private val dao: WeatherDao){


    suspend fun insertLocation(location: Location): Long {
        return dao.insertLocation(location)
    }

    suspend fun deleteLocation(location: Location): Int {
        return dao.deleteLocation(location)
    }

    suspend fun getAllLocations(): Flow<List<Location>> {
        return dao.getAllLocations()
    }



}