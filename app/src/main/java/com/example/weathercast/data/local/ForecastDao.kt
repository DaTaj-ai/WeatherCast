package com.example.weathercast.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weathercast.data.models.ForecastModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Upsert
    fun insertForecast(forecastModel: ForecastModel): Long

    @Delete
    fun deleteForecast(id: Int): Int

    @Query("SELECT * FROM forecast WHERE id = :id")
    fun getForecastById(id: Int): Flow<ForecastModel>

}