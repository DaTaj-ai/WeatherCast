package com.example.weathercast.data.repository

import android.content.SharedPreferences
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.mvvm_lab3.data.reomteDataSource.IRemoteDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RemoteDataSource
import com.example.weathercast.data.localDataSource.ILocalDataSource
import com.example.weathercast.data.models.Alarm
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

class Repository  constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource,
    private val shardPreferences: SharedPreferences
) : IRepository {

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

    override fun getWeather(lat: Double, lon: Double, lang: String, unit: String): Flow<WeatherModel?> {
        return flow {
            emit(remoteDataSource.getWeather(lat, lon, lang = lang, unit = unit))
        }
    }

    override fun getForecast(lat: Double, lon: Double, lang: String, unit: String): Flow<ForecastModel?> {
        return flow {
            emit(
                remoteDataSource.getForecast(lat = lat, lon = lon, lang = lang, unit = unit)?.body()
            )
        }
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // SharedPreferences
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    override fun getLanguage(): String? {
        return shardPreferences.getString(Constants.LANGUAGE, Constants.Emglish_PARM)
    }

    override fun getTemperatureUnit(): String? {
        return shardPreferences.getString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM)
    }

    override fun getLocationType(): String? {
        return shardPreferences.getString(Constants.LOCATION_TYPE, Constants.GPS_TYPE)
    }

    override fun getWindSpeed(): String? {
        return shardPreferences.getString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM)
    }

    override fun getGlobleLatLong(): LatLng {
        return LatLng(
            shardPreferences.getString(Constants.USER_LAT, "0.0")?.toDouble() ?: 0.0,
            shardPreferences.getString(Constants.USER_LONG, "0.0")?.toDouble() ?: 0.0
        )
    }
    override fun getCurrentWeatherId():Int {
        return shardPreferences.getInt(Constants.CURRENT_WEATHER_PRAM, Constants.CURRENT_WEATHER_ID )
    }


    override fun setForecast(list: List<ForecastEntry>){
        shardPreferences.edit().putString(Constants.FORECAST_DATA, list.toJsonList()).apply()
    }
    override fun `getLocalForecast`(): List<ForecastEntry> {
         val data = shardPreferences.getString(Constants.FORECAST_DATA, " ")
        return  data?.toForecastEntryList() ?: emptyList()
    }

    override fun setCurrentWeatherId(id: Int) {
        shardPreferences.edit().putInt(Constants.CURRENT_WEATHER_PRAM, id).apply()
    }

    override fun setLocationType(type: String) {
        shardPreferences.edit().putString(Constants.LOCATION_TYPE, type).apply()
    }

    override fun setLanguage(language: String) {
        shardPreferences.edit().putString(Constants.LANGUAGE, language).apply()
    }

    override fun setTemperatureUnit(unit: String) {
        shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, unit).apply()
    }

    override fun setWindSpeed(unit: String) {
        shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, unit).apply()
    }

    override fun setGlobleLatLong(lat: String, lon: String) {
        shardPreferences.edit().putString(Constants.USER_LAT, lat.toString()).apply()
        shardPreferences.edit().putString(Constants.USER_LONG, lon.toString()).apply()
    }



    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // Local part and Location
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    override suspend fun insertLocation(location: Location): Long {
        return localDataSource.insertLocation(location)
    }

    override suspend fun deleteLocation(location: Location): Int {
        return localDataSource.deleteLocation(location)
    }

    override suspend fun getAllLocations(): Flow<List<Location>> {
        return localDataSource.getAllLocations()
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // Local part and Weather
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


    override fun getLocalWeatherByLatLong(lat: Double, lon: Double): Flow<WeatherModel?> {
        return localDataSource.getLocalWeatherByLatLong(lat, lon)
    }

    override suspend fun getAllFavoriteWeather(): Flow<List<WeatherModel>> {
        return localDataSource.getAllWeather()
    }

    override suspend fun insertFavoriteWeather(weatherModel: WeatherModel): Long {
        return localDataSource.insertWeather(weatherModel)
    }

    override suspend fun deleteFavoriteWeather(weatherModel: WeatherModel): Int {
        return localDataSource.deleteWeather(weatherModel)
    }

    override suspend fun getWeatherById(id :Int): Flow<WeatherModel>{
        return localDataSource.getWeatherById(id)
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // Alarm
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    override suspend fun insertAlarm(alarm:Alarm):Long{
        return localDataSource.insertAlarm(alarm)
    }
    override suspend fun deleteAlarm(alarm:Alarm):Int{
        return localDataSource.deleteAlarm(alarm)
    }
    override fun getAllAlarm():Flow<List<Alarm>> {
        return localDataSource.getAllAlarm()

    }
}