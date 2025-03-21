package com.example.weathercast.data.repository

import android.util.Log
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RemoteDataSource
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    companion object {
        private var instance: Repository? = null
        fun getInstance(remote: RemoteDataSource, local: LocalDataSource): Repository {
            return instance ?: Repository(remote, local).also { instance = it }
        }
    }

    fun getWeather(lat: Double, lon: Double , lang: String  , unit: String): Flow<WeatherModel?> {
        return flow {
            emit(remoteDataSource.getWeather(lat, lon , lang = lang , unit=  unit))
        }
    }

    fun getForecast(lat: Double, lon: Double, lang: String , unit: String): Flow<ForecastModel?> {
        return flow {
            emit(remoteDataSource.getForecast(lat, lon ,lang , unit )?.body())
        }
    }

}