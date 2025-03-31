package com.example.weathercast.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.data.repository.Repository
import com.example.weathercast.utlis.Constants


class SettingsViewModel(var repo : Repository) : ViewModel(){

    fun getLanguage(): String? {
        return repo.getLanguage()
    }

    fun getTemperatureUnit(): String? {
        return repo.getTemperatureUnit()
    }

    fun getLocationType(): String? {
        return repo.getLocationType()
    }
    fun setLocationType(type: String) {
        repo.setLocationType(type)
    }
    fun setLanguage(language: String) {
        repo.setLanguage(language)
    }
    fun setTemperatureUnit(unit: String) {
        repo.setTemperatureUnit(unit)
    }
    fun setWindSpeed(unit :String){
        repo.setWindSpeed(unit)
    }


}

class SettingsViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repo) as T
    }
}