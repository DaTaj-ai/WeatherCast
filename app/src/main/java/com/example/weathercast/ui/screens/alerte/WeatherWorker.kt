package com.example.weathercast.ui.screens.alerte

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab4workmanager.database.WeatherDataBase
import com.example.mvvm_lab3.data.localDataSource.LocalDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RemoteDataSource
import com.example.mvvm_lab3.data.reomteDataSource.RetrofitClient
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Constants
import kotlinx.coroutines.flow.first

class WeatherWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repository = Repository.getInstance(
        RemoteDataSource(RetrofitClient.apiService),
        LocalDataSource(
            WeatherDataBase.getInstance(context).getWeatherDao(),
            WeatherDataBase.getInstance(context).getAlarmDao()
        ), context.applicationContext.getSharedPreferences(
            Constants.SETTINGS,
            Context.MODE_PRIVATE
        )
    )

    override suspend fun doWork(): Result {
        return try {
            // Get input data
            val withSound = inputData.getBoolean("WITH_SOUND", false)
            val alarmTime = inputData.getLong("ALARM_TIME", 0L)

            val weatherResponse =
                repository.getWeather(1.1, 1.1, Constants.Emglish_PARM, Constants.CELSIUS_PARM)
                    .first()

            alarmNotification(
                applicationContext,
                "Weather Cast Notification ${weatherResponse?.weather?.get(0)?.description}",
                "Alarm Fired!"
            )
            Result.success()

        } catch (e: Exception) {
            alarmNotification(applicationContext, "Weather Cast Notification", "Alarm Fired!")
            Result.retry()
        }
    }


}