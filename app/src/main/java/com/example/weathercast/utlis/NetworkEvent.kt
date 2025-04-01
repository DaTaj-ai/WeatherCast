package com.example.weathercast.utlis

sealed class NetworkEvent {
    object Connected : NetworkEvent()
    object Disconnected : NetworkEvent()
}
