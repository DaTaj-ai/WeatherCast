//package com.example.weathercast.navigation
//
//import android.os.Bundle
//import androidx.navigation.NavType
//import com.example.weathercast.ui.screens.home.components.ForecastItem
//import com.google.gson.Gson
//
//abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
//    abstract fun fromJsonParse(value: String): T
//    abstract fun T.getJsonParse(): String
//
//    override fun get(bundle: Bundle, key: String): T? =
//        bundle.getString(key)?.let { parseValue(it) }
//
//    override fun parseValue(value: String): T = fromJsonParse(value)
//
//    override fun put(bundle: Bundle, key: String, value: T) {
//        bundle.putString(key, value.getJsonParse())
//    }
//}
//
//
//class forecastItemArgType : JsonNavType<ForecastItem>() {
//    override fun fromJsonParse(value: String): ForecastItem = Gson().fromJson(value, ForecastItem::class.java)
//
//    override fun ForecastItem.getJsonParse(): String  = Gson().toJson(this)
//
//}

