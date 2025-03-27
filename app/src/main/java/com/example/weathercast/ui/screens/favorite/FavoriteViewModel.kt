package com.example.weathercast.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.models.Location
import com.example.weathercast.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    val _mutableStateLocationList = MutableStateFlow<List<Location>>(emptyList())
    val locations = _mutableStateLocationList.asStateFlow()


    fun insertLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertLocation(location)
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocation(location)
        }
    }

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllLocations().collect {
                _mutableStateLocationList.emit(it)
            }
        }
    }

}


class FavoritesScreenViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repo) as T
    }
}