package com.example.weathercast.data.repository

import com.example.mvvm_lab3.data.reomteDataSource.IRemoteDataSource
import com.example.weathercast.data.models.ForecastModel
import com.example.weathercast.data.models.WeatherModel
import retrofit2.Response

class FakeRemoteDataSource : IRemoteDataSource {

    override suspend fun getForecast(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Response<ForecastModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun getWeather(lat: Double, lon: Double, lang: String, unit: String): WeatherModel? {
        return null
    }

}