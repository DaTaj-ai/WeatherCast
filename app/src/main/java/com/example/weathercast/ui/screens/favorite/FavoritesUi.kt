package com.example.weathercast.ui.screens.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.ui.screens.favorite.components.FavoriteLocationUi
import com.example.weathercast.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreenMain(
    favoriteViewModel: FavoriteViewModel,
    navigateToDetails: (WeatherModel) -> Unit,
    navigateToMap: () -> Unit
) {
    var showMap by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Favorite Locations",
                        modifier = Modifier.background(Color.Transparent).padding(16.dp)
                        ,style = MaterialTheme.typography.titleMedium,
                        fontSize = 25.sp,
                        color = Primary
                    )
                },
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                    )
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 80.dp),
                onClick = { navigateToMap() },
                containerColor = Primary,
                contentColor = Color.White,
                elevation = elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 12.dp,
                    focusedElevation = 8.dp,
                    hoveredElevation = 8.dp
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Add to favorites",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingsValue ->
        FavoriteLocationUi(favoriteViewModel, snackBarHostState, navigateToDetails , modifier = Modifier.padding(paddingsValue))
    }
}
