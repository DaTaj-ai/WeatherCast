package com.example.weathercast.data.repository

import com.example.weathercast.data.localDataSource.ILocalDataSource
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource: ILocalDataSource {
    override suspend fun insertLocation(location: Location): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(location: Location): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLocations(): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?> {
        TODO("Not yet implemented")
    }

    override fun insertWeather(weatherModel: WeatherModel): Long {
        TODO("Not yet implemented")
    }

    override fun deleteWeather(weatherModel: WeatherModel): Int {
        TODO("Not yet implemented")
    }

    override fun getAllWeather(): Flow<List<WeatherModel>> {
        TODO("Not yet implemented")
    }

    override fun getWeatherById(id: Int): Flow<WeatherModel> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlarm(alarm: Alarm): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlarm(alarm: Alarm): Int {
        TODO("Not yet implemented")
    }

    override fun getAllAlarm(): Flow<List<Alarm>> {
        TODO("Not yet implemented")
    }
}