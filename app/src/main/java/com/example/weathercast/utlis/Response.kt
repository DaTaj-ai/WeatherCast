package com.example.weathercast.utlis

sealed class Response {
    data object Loading : Response()
    data class Success(val data: Any) : Response()
    data class Error(val message: String) : Response()
}