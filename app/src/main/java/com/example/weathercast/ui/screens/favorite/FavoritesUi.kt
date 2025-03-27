package com.example.weathercast.ui.screens.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weathercast.utlis.Constants

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreenMain(favoriteViewModel: FavoriteViewModel) {
    var showMap by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Locations ") },
                modifier = Modifier.background(Color.Transparent))

            if (showMap) {
                ShowGoogleMap(favoriteViewModel , Constants.MAP_FAVORITES_TYPE)
            } else {
                FavoriteLocationUi(favoriteViewModel ,snackBarHostState  )
            }
                 },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 70.dp),
                onClick = { showMap = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
        ,
         snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        if (showMap) {
            ShowGoogleMap(favoriteViewModel , Constants.MAP_FAVORITES_TYPE)
        } else {
            FavoriteLocationUi(favoriteViewModel ,snackBarHostState  )
        }
    }
}
