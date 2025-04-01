package com.example.weathercast.ui.screens.alerte.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.data.models.getFormattedDate
import com.example.weathercast.data.models.getFormattedTime
import com.example.weathercast.ui.screens.alerte.AlarmViewModel
import com.example.weathercast.ui.screens.favorite.components.SwipeToDeleteContainer
import com.example.weathercast.ui.theme.Primary



@Composable
private fun AlarmCardItem(alarm: Alarm)
{
    Card(
        modifier = Modifier
            .padding(8.dp)
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
                            Color(0xFF00D2FF)
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

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = alarm.getFormattedTime(),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold, color = Primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = alarm.getFormattedDate(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Thin,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AlarmContent(list: List<Alarm>, modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState,deleteAlarm:(Alarm)->Unit) {
    LazyColumn (modifier = modifier) {
        items(list.size) {
            SwipeToDeleteContainer(
                item =list[it],
                onDelete = {
                    deleteAlarm(it)
                },
                snackbarHostState = snackbarHostState,
            ) {
                AlarmCardItem(it)
            }

        }
    }
}
