package com.example.weathercast.utlis

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class NetworkMonitor(context: Context) {

    @SuppressLint("ServiceCast")
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}