package com.example.weathercast.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weathercast.data.models.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Upsert
    fun insertAlarm(alarm: Alarm): Long

    @Delete
    suspend fun deleteAlarm(alarm: Alarm): Int

    @Query("select * from AlarmTable")
    fun getAllAlarm(): Flow<List<Alarm>>
}