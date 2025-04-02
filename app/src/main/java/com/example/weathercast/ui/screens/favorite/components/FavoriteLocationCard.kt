package com.example.weathercast.ui.screens.favorite.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.R
import com.example.weathercast.data.models.WeatherModel
import com.example.weathercast.ui.screens.favorite.FavoriteViewModel
import com.example.weathercast.ui.theme.MyLightBlue
import com.example.weathercast.ui.theme.Primary
import com.example.weathercast.utlis.weatherIcons


@Composable
fun FavoriteLocationCardItem(
    location: WeatherModel,
    navigateToDetailsScreen: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navigateToDetailsScreen() }
            .fillMaxWidth()
            .height(130.dp),
        elevation = cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF3A7BD5),
                            MyLightBlue
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side content
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(24.dp), tint = Primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = location.name,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Text(
                            text = location.weather[0].description,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 4.dp),
                            color = Primary
                        )
                    }
                }

                // Right side content
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = weatherIcons[location.weather[0].icon] ?: R.drawable.day_clear
                        ),
                        contentDescription = "Weather icon",
                        modifier = Modifier.size(50.dp)
                            .padding(end = 16.dp)
                    )
                    Text(
                        text = "${location.main.temp.toInt()}°C",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                        , color = Primary
                    )
                }
            }
        }
    }
}


@Composable
fun FavoriteLocationUi(
    favoriteViewModel: FavoriteViewModel,
    snackbarHostState: SnackbarHostState,
    navigateToDetails: (WeatherModel) -> Unit,
    modifier: Modifier = Modifier
) {
    favoriteViewModel.getAllFavoriteLocation()
    val locations by favoriteViewModel.weatherList.collectAsStateWithLifecycle()
    LazyColumn(modifier = modifier) {
        items(locations.size) {
            SwipeToDeleteContainer(
                item = locations[it],
                onDelete = {
                    favoriteViewModel.deleteWeather(it)
                },
                snackbarHostState = snackbarHostState,
            ) {
                FavoriteLocationCardItem(it, { navigateToDetails(it) })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    onRestore: (T) -> Unit = {},
    animationDuration: Int = 100,
    snackbarHostState: SnackbarHostState,
    content: @Composable (T) -> Unit,
) {
    val context = LocalContext.current
    var isRemoved by remember { mutableStateOf(false) }
    var canSwipe by remember { mutableStateOf(true) }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                canSwipe = false
                Log.i("TAG", "SwipeToDeleteContainer 1 : ")
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isRemoved) {
        Log.i("TAG", "SwipeToDeleteContainer 1 : ")
        if (isRemoved) {
            Log.i("TAG", "SwipeToDeleteContainer 1 : ")
            val result = snackbarHostState.showSnackbar(
                message = "راجع نفسك يا صاحبي  ?!  ",
                actionLabel = "متشغلش بالك",
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                onRestore(item)
                canSwipe = true
                isRemoved = false
            } else {
//                delay(animationDuration.toLong())
                onDelete(item)
            }
        }
    }



    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(durationMillis = animationDuration)
        ) + fadeOut()
    ) {
        if (canSwipe) {
            SwipeToDismissBox(
                state = state,
                backgroundContent = { DeleteBackground(state) },
                enableDismissFromStartToEnd = false
            ) {
                content(item)
            }
        } else {
            LaunchedEffect(Unit) {
                state.snapTo(SwipeToDismissBoxValue.Settled)
            }
            content(item)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else {
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .padding(start = 24.dp)
            .background(color),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.padding(16.dp),
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White,
        )
    }
}
