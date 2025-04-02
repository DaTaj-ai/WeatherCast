package com.example.weathercast.data.repository

import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.models.ForecastEntry
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getWeather(lat: Double, lon: Double, lang: String, unit: String): Flow<WeatherModel?>
    fun getForecast(lat: Double, lon: Double, lang: String, unit: String): Flow<ForecastModel?>

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // SharedPreferences
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    fun getLanguage(): String?
    fun getTemperatureUnit(): String?
    fun getLocationType(): String?
    fun getWindSpeed(): String?
    fun getGlobleLatLong(): LatLng
    fun getCurrentWeatherId(): Int
    fun setForecast(list: List<ForecastEntry>)
    fun `getLocalForecast`(): List<ForecastEntry>
    fun setCurrentWeatherId(id: Int)
    fun setLocationType(type: String)
    fun setLanguage(language: String)
    fun setTemperatureUnit(unit: String)
    fun setWindSpeed(unit: String)
    fun setGlobleLatLong(lat: String, lon: String)

    suspend fun insertLocation(location: Location): Long

    suspend fun deleteLocation(location: Location): Int

    suspend fun getAllLocations(): Flow<List<Location>>
    fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?>

    suspend fun getAllFavoriteWeather(): Flow<List<WeatherModel>>

    suspend fun insertFavoriteWeather(weatherModel: WeatherModel): Long

    suspend fun deleteFavoriteWeather(weatherModel: WeatherModel): Int

    suspend fun getWeatherById(id: Int): Flow<WeatherModel>
    suspend fun insertAlarm(alarm: Alarm): Long
    suspend fun deleteAlarm(alarm: Alarm): Int
    fun getAllAlarm(): Flow<List<Alarm>>
}