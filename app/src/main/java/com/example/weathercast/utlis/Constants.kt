package com.example.weathercast.utlis

class Constants {
    companion object {
        val SETTINGS = "Settings"
        val LANGUAGE = "Language"
        val TEMPERATURE_UNIT = "Temperature Unit"
        val LOCATION_TYPE = "Location"
        val WIND_SPEED_UNIT = "Wind Speed Unit"
        val ARABIC_PARM = "ar"
        val Emglish_PARM = "en"
        val FAHRENHEIT_PARM = "imperial"
        val CELSIUS_PARM = "metric"
        val KELVIN_PARM = ""
        val USER_LONG = "USER_LOCAL_LONGITUDE"
        val USER_LAT = "USER_LOCAL_LATITUDE"
        val MAP_FAVORITES_TYPE = "MAP_FAVORITE"
        val MAP_PICK_TYPE = "MAP_SEARCH"

        val GPS_TYPE = "GPS"
        val MAP_TYPE = "MAP"
    }
}

//
//For temperature in Fahrenheit use units=imperial
//For temperature in Celsius use units=metric
//Temperature in Kelvin is used by default, no need to use units parameter in API call