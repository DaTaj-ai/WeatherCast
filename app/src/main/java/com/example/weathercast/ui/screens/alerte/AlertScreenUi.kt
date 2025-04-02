package com.example.weathercast.ui.screens.alerte

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
            DateAndTimePicker(
                onDismiss = { showDatePicker = false },
                { it ->
                    alarmViewModel.insertAlarm(it)
                }, context = context
            )
        }
    }
}


@SuppressLint("ScheduleExactAlarm")
private fun setAlarm(context: Context, time: Long , withSound:Boolean) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MyAlarm::class.java).apply {
        putExtra("WITH_SOUND", withSound)
    }

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
fun DateAndTimePicker(
    onDismiss: () -> Unit,
    insertAlarm: (Alarm) -> Unit,
    context: Context
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) } // New state for confirmation dialog
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var triggerTimeMillis by remember { mutableStateOf(0L) } // Store the calculated time

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Confirmation Dialog (shown last)
        if (showConfirmationDialog) {
            var selectedOption by remember { mutableStateOf("notification") } // Track selected option

            AlertDialog(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Confirm Reminder") },
                text = {
                    Column {
                        Text(
                            "Set reminder for ${selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))} " +
                                    "at ${selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}?"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Notification option
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOption = "notification" }
                        ) {
                            RadioButton(
                                selected = (selectedOption == "notification"),
                                onClick = { selectedOption = "notification" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "Notification Only",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Alarm option
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOption = "alarm" }
                        ) {
                            RadioButton(
                                selected = (selectedOption == "alarm"),
                                onClick = { selectedOption = "alarm" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "Alarm with Sound",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (selectedOption == "alarm") {
                                setAlarm(context, triggerTimeMillis , true)
                            }
                            else{
                                setAlarm(context, triggerTimeMillis , false)
                            }
                            insertAlarm(Alarm(
                                dateTime = triggerTimeMillis
                            ))
                            showConfirmationDialog = false
                            onDismiss()
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showConfirmationDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        // 2. Time Picker Dialog (shown second)
        if (showTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = selectedTime.hour,
                initialMinute = selectedTime.minute
            )

            AlertDialog(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    Button(onClick = {
                        selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        triggerTimeMillis = convertToMillis(selectedDate, selectedTime)
                        showTimePicker = false
                        showConfirmationDialog = true // Show confirmation instead of dismissing
                    }) {
                        Text("Next")
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

        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                // ... (keep your existing color configuration)
            ),
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { epochMilli ->
                        selectedDate = LocalDate.ofEpochDay(epochMilli / (24 * 60 * 60 * 1000))
                    }
                    showTimePicker = true // Proceed to time picker
                }) {
                    Text("Next")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

