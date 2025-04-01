package com.example.weathercast.ui.screens.alerte

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathercast.data.models.Alarm
import com.example.weathercast.ui.screens.alerte.components.AlarmContent
import com.example.weathercast.ui.theme.Primary
import com.example.weathercast.utlis.Response
import com.example.weathercast.utlis.convertToMillis
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlarmScreenUi(alarmViewModel: AlarmViewModel) {

    var alarmList = alarmViewModel.alarmListState.collectAsStateWithLifecycle()

    var showDatePicker by remember { mutableStateOf(false) }

    var snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    alarmViewModel.getAllAlarm()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alarm",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 80.dp),
                onClick = { showDatePicker = true },
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
                    imageVector = Icons.Default.AddAlert,
                    contentDescription = "Add to favorites",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->

        when (alarmList.value) {
            is Response.Error -> {

            }

            is Response.Success -> {
                AlarmContent(
                    modifier = Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding() + 80.dp
                        )
                        .fillMaxSize(),
                    list = (alarmList.value as Response.Success).data as List<Alarm>,
                    snackbarHostState = snackbarHostState,
                    deleteAlarm = {
                        alarmViewModel.deleteAlarm(it)
                    })
            }


            is Response.Error -> {
                Text(
                    text = "Error: $",
                )
            }

            Response.Loading ->
                Text(
                    text = "Loading: $",
                )
        }


        if (showDatePicker) {
            DateAndTimePickerExample(
                onDismiss = { showDatePicker = false },
                { it ->
                    alarmViewModel.insertAlarm(it)
                }, context = context
            )
        }


    }
}


@SuppressLint("ScheduleExactAlarm")
private fun setAlarm(context: Context, time: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MyAlarm::class.java)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        else
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimePickerExample(
    onDismiss: () -> Unit,
    insertAlarm: (Alarm) -> Unit,
    context: Context
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000)

    val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    val formattedTime = selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selected Date: $formattedDate", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Selected Time: $formattedTime", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showTimePicker = true }) {
            Text(text = "Select Date and Time")
        }

        // TimePicker Dialog
        if (showTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = selectedTime.hour,
                initialMinute = selectedTime.minute
            )

            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    Button(onClick = {

                        selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        showTimePicker = false
                        val triggerTimeMillis = convertToMillis(selectedDate, selectedTime)
                        setAlarm(context, triggerTimeMillis)

                        insertAlarm(Alarm(dateTime = triggerTimeMillis))
                        onDismiss()

                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(onClick = { showTimePicker = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Select Time") },
                text = { TimePicker(state = timePickerState) }
            )
        }

        // Date Picker Dialog
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = {
                    val epochMilli = datePickerState.selectedDateMillis
                    if (epochMilli != null) {
                        selectedDate = LocalDate.ofEpochDay(epochMilli / (24 * 60 * 60 * 1000))
                    }
                    showTimePicker = true // Show time picker after selecting date
                }) {
                    Text("Next")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}



