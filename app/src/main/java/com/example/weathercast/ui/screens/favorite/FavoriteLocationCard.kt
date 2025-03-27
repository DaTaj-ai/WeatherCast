package com.example.weathercast.ui.screens.favorite

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.R
import com.example.weathercast.data.models.Location


@Composable
fun FavoriteLocationCardItem(location: Location) {
    val snackbarHostState = remember { SnackbarHostState() }
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(130.dp),
        elevation = cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(Color(0xff1680f5))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row {
                Column() {
                    Text(
                        text = location.city, fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = location.country,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 2.dp)
                    )
                }
                Icon(Icons.Default.LocationOn, contentDescription = "Add Item")
            }

            Image(
                painter = painterResource(id = R.drawable.cloud_day_forecast_rain_rainy_icon),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(top = 4.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "25",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically), fontSize = 20.sp
            )
        }
    }
}


@Composable
fun FavoriteLocationUi(favoriteViewModel: FavoriteViewModel ,  snackbarHostState: SnackbarHostState) {
    favoriteViewModel.getLocations()
    val locations by favoriteViewModel.locations.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(locations.size) {
            SwipeToDeleteContainer(
                item = locations[it],
                onDelete = {
                    Log.i("TAG", "FavoriteLocationUi: ${it.city} , ${it.country} ")
                    favoriteViewModel.deleteLocation(it) },
                snackbarHostState = snackbarHostState
            ) {
                FavoriteLocationCardItem(it)
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
    animationDuration: Int = 500,
    snackbarHostState: SnackbarHostState,
    content: @Composable (T) -> Unit
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
                message = "how are you ya saad ",
                actionLabel = "undo",
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                Log.i("TAG", "SwipeToDeleteContainer: yes i am here ??  ")
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
