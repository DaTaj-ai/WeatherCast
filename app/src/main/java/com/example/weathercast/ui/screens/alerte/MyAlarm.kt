package com.example.weathercast.ui.screens.alerte

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weathercast.R
import com.google.android.datatransport.cct.internal.NetworkConnectionInfo

class MyAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            // Extract data from intent
            val withSound = intent.getBooleanExtra("WITH_SOUND", false)
            val alarmTime = intent.getLongExtra("ALARM_TIME", 0L)

            // Create input data for worker
            val inputData = Data.Builder()
                .putBoolean("WITH_SOUND", withSound)
                .putLong("ALARM_TIME", alarmTime)
                .build()

            // Set constraints (e.g., require network)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            // Build work request
            val weatherWork = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setInputData(inputData)
                .setConstraints(constraints)
                .build()

            // Enqueue work
            WorkManager.getInstance(context).enqueue(weatherWork)

            // Immediate notification to acknowledge alarm
            if (withSound) {
                alarmNotification(context, "Weather Update", "Fetching weather data...")
            } else {
                showNotification(context, "Weather Update", "Fetching weather data...")
            }

        } catch (e: Exception) {
            Log.e("MyAlarm", "Error scheduling weather update", e)
        }
    }

//class MyAlarm : BroadcastReceiver() {
//    @SuppressLint("SuspiciousIndentation")
//    override fun onReceive(context: Context, intent: Intent) {
//        try {
//            val withSound = intent.getBooleanExtra("WITH_SOUND", false)
//
//            if (withSound) {
//                alarmNotification(context, "Weather Cast Notification", "Alarm Fired!")
//            } else
//            showNotification(context, "Weather Cast Notification", "Alarm Fired!")
//        } catch (ex: Exception) {
//            Log.d("Receive Ex", "onReceive: ${ex.printStackTrace()}")
//        }
//    }
//}
}
fun alarmNotification(
    context: Context,
    title: String,
    desc: String
) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "alarm_cفثhannel"
    val channelName = "Alarm Notifications"


    val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {

            Log.i("alarm", "showNotification: 1 ")
            setSound(soundUri, null)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.cloud_day_forecast_rain_rainy_icon)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    Log.i("TAG", "showNotification: 2  ")
    builder.setSound(soundUri)

    manager.notify(1, builder.build())
}


fun showNotification(
    context: Context,
    title: String,
    desc: String
) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "Notification"
    val channelName = "Notifications"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.cloud_day_forecast_rain_rainy_icon)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    manager.notify(1, builder.build())
}
