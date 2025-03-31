package com.example.weathercast.ui.screens.alerte

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showSystemUi = true)
fun AlertScreenMain() {
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AlarmManager Notification",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 70.dp),
                onClick = { showDatePicker = true } // Open Date Picker Dialog
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Alert Screen")
            Text(
                text = "Set Alarm Time: 10 Seconds",
                modifier = Modifier.padding(10.dp),
                fontSize = 16.sp
            )
            Button(
                onClick = { Log.i("TAG", "AlertScreenMain: ") }
            ) {
                Text(text = "Set Alarm")
            }
        }
        if (showDatePicker) {
            DateAndTimePickerExample(onDismiss = { showDatePicker = false }, context = context)
        }
    }
}


@SuppressLint("ScheduleExactAlarm")
private fun setAlarm(context: Context, time : Long  ) {
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
fun DateAndTimePickerExample(onDismiss: () -> Unit, context: Context) {
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000)

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

                        // Convert selected date and time to milliseconds
                        val triggerTimeMillis = convertToMillis(selectedDate, selectedTime)

                        // Set the alarm
                        setAlarm(context, triggerTimeMillis)

                        // Dismiss dialog
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



@RequiresApi(Build.VERSION_CODES.O)
fun convertToMillis(date: LocalDate, time: LocalTime): Long {
    val zoneId = java.time.ZoneId.systemDefault()
    return date.atTime(time).atZone(zoneId).toInstant().toEpochMilli()
}
