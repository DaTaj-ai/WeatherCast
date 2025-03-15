package com.example.weathercast.data.repository

import android.util.Log
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RemoteDataSource

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
//    companion object {
//        @Volatile
//        private var instance: Repository? = null
//        fun getInstance(
//            remoteDataSource: RemoteDataSource,
//            /*localDataSource: LocalDataSource*/
//        ): Repository {
//            return instance ?: synchronized(this) {
//                instance ?: Repository(remoteDataSource).also { instance = it }
//            }
//        }
//    }


    suspend fun getWeather(lat: Double, lon: Double) {
        val weather = remoteDataSource.getWeather(lat, lon)

        Log.i("TAG", "getWeather:${weather.toString()} from repository + ")
    }

}