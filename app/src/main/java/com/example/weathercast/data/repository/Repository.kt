package com.example.weathercast.data.repository

import android.util.Log
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RemoteDataSource
import com.example.weathercast.data.models.ForecastModel

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    companion object{
        private var instance: Repository? = null
        fun getInstance(remote: RemoteDataSource, local: LocalDataSource): Repository {
            return instance ?: Repository(remote, local).also { instance = it }
        }
    }

    suspend fun getWeather(lat: Double, lon: Double) {
        val weather = remoteDataSource.getWeather(lat, lon)

        Log.i("TAG", " we are here ${weather?.weather.toString()}${weather?.id} from repository + ")
    }

    suspend fun getForecast(lat: Double, lon: Double): ForecastModel? {
        val forecast = remoteDataSource.getForecast(lat, lon)
        Log.i("TAG", "Forecast data  ")
        Log.i("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        for(i in forecast?.body()?.list!!){
            Log.i("TAG", "getForecast: ${i.weather[0].description}")
            Log.i("TAG", "getForecast: ${i.weather.toString()}")
        }
        Log.i("TAG", "getForecast  : ${forecast?.body()?.list}")
        Log.i("TAG", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        return forecast?.body()
    }
}