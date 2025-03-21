package com.example.weathercast.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.data.repository.Repository


class SettingsViewModel(repo : Repository) : ViewModel(){

    init {

    }

}

class SettingsViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repo) as T
    }
}