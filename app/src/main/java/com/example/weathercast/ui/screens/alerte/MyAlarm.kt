package com.example.weathercast.ui.screens.alerte

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
import com.example.weathercast.R


class MyAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            showNotification(context, "Weather Cast Notification", "Alarm Fired!")
        } catch (ex: Exception) {
            Log.d("Receive Ex", "onReceive: ${ex.printStackTrace()}")
        }
    }
}

private fun showNotification(context: Context, title: String, desc: String) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "alarm_cفثhannel"
    val channelName = "Alarm Notifications"

    // Set the default alarm sound
    val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(soundUri, null) // Set the notification sound
            enableVibration(true) // Enable vibration if needed
        }
        manager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.cloud_day_forecast_rain_rainy_icon)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSound(soundUri) // Set the sound for older versions (pre-Oreo)

    manager.notify(1, builder.build())
}




