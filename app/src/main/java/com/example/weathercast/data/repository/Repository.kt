package com.example.weathercast.data.repository

import android.content.SharedPreferences
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RemoteDataSource
import com.example.weathercast.data.models.ForecastEntry
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.data.models.toForecastEntryList
import com.example.weathercast.data.models.toJsonList
import com.example.weathercast.utlis.Constants
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val shardPreferences: SharedPreferences
) {

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            remote: RemoteDataSource,
            local: LocalDataSource,
            shardPreferences: SharedPreferences
        ): Repository {
            return instance ?: Repository(remote, local, shardPreferences).also { instance = it }
        }
    }

    fun getWeather(lat: Double, lon: Double, lang: String, unit: String): Flow<WeatherModel?> {
        return flow {
            emit(remoteDataSource.getWeather(lat, lon, lang = lang, unit = unit))
        }
    }

    fun getForecast(lat: Double, lon: Double, lang: String, unit: String): Flow<ForecastModel?> {
        return flow {
            emit(
                remoteDataSource.getForecast(lat = lat, lon = lon, lang = lang, unit = unit)?.body()
            )
        }
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // SharedPreferences
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    fun getLanguage(): String? {
        return shardPreferences.getString(Constants.LANGUAGE, Constants.Emglish_PARM)
    }

    fun getTemperatureUnit(): String? {
        return shardPreferences.getString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM)
    }

    fun getLocationType(): String? {
        return shardPreferences.getString(Constants.LOCATION_TYPE, Constants.GPS_TYPE)
    }

    fun getWindSpeed(): String? {
        return shardPreferences.getString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM)
    }

    fun getGlobleLatLong(): LatLng {
        return LatLng(
            shardPreferences.getString(Constants.USER_LAT, "0.0")?.toDouble() ?: 0.0,
            shardPreferences.getString(Constants.USER_LONG, "0.0")?.toDouble() ?: 0.0
        )
    }
    fun getCurrentWeatherId():Int {
        return shardPreferences.getInt(Constants.CURRENT_WEATHER_PRAM, Constants.CURRENT_WEATHER_ID )
    }


    fun setForecast(list: List<ForecastEntry>){
        shardPreferences.edit().putString(Constants.FORECAST_DATA, list.toJsonList()).apply()
    }
    fun `getLocalForecast`(): List<ForecastEntry> {
         val data = shardPreferences.getString(Constants.FORECAST_DATA, " ")
        return  data?.toForecastEntryList() ?: emptyList()
    }

    fun setCurrentWeatherId(id: Int) {
        shardPreferences.edit().putInt(Constants.CURRENT_WEATHER_PRAM, id).apply()
    }

    fun setLocationType(type: String) {
        shardPreferences.edit().putString(Constants.LOCATION_TYPE, type).apply()
    }

    fun setLanguage(language: String) {
        shardPreferences.edit().putString(Constants.LANGUAGE, language).apply()
    }

    fun setTemperatureUnit(unit: String) {
        shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, unit).apply()
    }

    fun setWindSpeed(unit: String) {
        shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, unit).apply()
    }

    fun setGlobleLatLong(lat: String, lon: String) {
        shardPreferences.edit().putString(Constants.USER_LAT, lat.toString()).apply()
        shardPreferences.edit().putString(Constants.USER_LONG, lon.toString()).apply()
    }



    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // Local part and Location
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    suspend fun insertLocation(location: Location): Long {
        return localDataSource.insertLocation(location)
    }

    suspend fun deleteLocation(location: Location): Int {
        return localDataSource.deleteLocation(location)
    }

    suspend fun getAllLocations(): Flow<List<Location>> {
        return localDataSource.getAllLocations()
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // Local part and Weather
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


    fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?> {
        return localDataSource.getLocalWeatherByLatLong(lat, lon)
    }

    suspend fun getAllFavoriteWeather(): Flow<List<WeatherModel>> {
        return localDataSource.getAllWeather()
    }

    suspend fun insertFavoriteWeather(weatherModel: WeatherModel): Long {
        return localDataSource.insertWeather(weatherModel)
    }

    suspend fun deleteFavoriteWeather(weatherModel: WeatherModel): Int {
        return localDataSource.deleteWeather(weatherModel)
    }

    suspend fun getWeatherById(id :Int): Flow<WeatherModel>{
        return localDataSource.getWeatherById(id)
    }


}