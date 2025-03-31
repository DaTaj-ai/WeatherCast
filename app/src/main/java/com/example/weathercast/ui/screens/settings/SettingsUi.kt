package com.example.weathercast.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercast.ui.theme.Primary
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.restartActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenMain(settingsViewModel: SettingsViewModel, navigateToMap: () -> Unit) {
    var context = LocalContext.current.applicationContext

    val languageMap = mapOf(
        "Language" to Pair(listOf("English", "عربي", "Default")) { selected: String ->
            if (selected == "عربي") {
                settingsViewModel.setLanguage(Constants.ARABIC_PARM)
            } else if (selected == "Default") {
                settingsViewModel.setLanguage(Constants.Emglish_PARM)
            } else {
                settingsViewModel.setLanguage(Constants.Emglish_PARM)
            }
            restartActivity(context)
            Log.i("TAG", "SettingsScreenMain: Selected language $selected")
        }
    )

    val tempUnitMap = mapOf(
        "Temperature Unit" to Pair(listOf("Celsius", "Fahrenheit", "Kelvin")) { selected: String ->
            if (selected == "Celsius") {
                settingsViewModel.setTemperatureUnit(Constants.CELSIUS_PARM)
                //shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM).apply()
            } else if (selected == "Fahrenheit") {
                settingsViewModel.setTemperatureUnit(Constants.FAHRENHEIT_PARM)
                //shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, Constants.FAHRENHEIT_PARM).apply()
            } else {
                settingsViewModel.setTemperatureUnit(Constants.KELVIN_PARM)
                //shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, Constants.KELVIN_PARM).apply()
            }
            Log.i("TAG", "SettingsScreenMain: Selected temperature unit $selected")
        }
    )

    val locationMap = mapOf(
        "Location" to Pair(listOf(Constants.GPS_TYPE, Constants.MAP_TYPE)) { selected: String ->

            settingsViewModel.setLocationType(selected)
            //shardPreferences.edit().putString(Constants.LOCATION_TYPE, selected).apply()

            if (selected == Constants.MAP_TYPE) {
                navigateToMap()
            }

            Log.i("TAG", "SettingsScreenMain: Selected location $selected")
        }
    )

    val windSpeedUnitMap = mapOf(
        "Wind Speed Unit" to Pair(listOf("meter/sec", "mile/hour")) { selected: String ->
            settingsViewModel.setWindSpeed(selected)
            //shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, selected).apply()
            Log.i("TAG", "SettingsScreenMain: Selected wind speed unit $selected")
        }
    )

    // List of all settings maps
    val list = listOf(languageMap, tempUnitMap, locationMap, windSpeedUnitMap)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue) // Background for the whole screen
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .background(color = Primary)
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            "Preferences",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 25.sp, color = Color.White
                        )
                    }
                    items(list.size) { item ->
                        RadioButtonSingleSelection(list[item].entries.elementAt(0))
                    }
                }
            }
        )
    }
}

@Composable
fun RadioButtonSingleSelection(data: Map.Entry<String, Pair<List<String>, (String) -> Any>>) {
    val (label, value) = data
    val (options, callback) = value
    var selectedOption by remember { mutableStateOf(options[0]) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            options.forEach { text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                selectedOption = text
                                callback(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null, // handled by parent selectable
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp),
//                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}


//@Composable
//fun RadioButtonSingleSelection(data: Map.Entry<String, Pair<List<String>, (String) -> Int>>) {
//
//    val (label, value) = data
//    val (options, callback) = value
//
//    var selectedOption by remember { mutableStateOf(options[0]) }
//
//    Card(
//        modifier = Modifier.padding(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column() {
//            Text(
//                text = label,
//                color = Color.Black,
//                fontSize = 20.sp,
//                modifier = Modifier.padding(16.dp)
//            )
//
//            Row(modifier = Modifier.fillMaxWidth()) {
//                options.forEach { text ->
//                    Column(
//                        Modifier
//                            .height(80.dp)
//                            .align(Alignment.CenterVertically)
//                            .padding(top = 16.dp)
//                            .selectable(
//                                selected = (text == selectedOption),
//                                onClick = { selectedOption = text
//                                    callback(text) },
//                                role = Role.RadioButton )
//                            .padding(horizontal = 16.dp) ,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
////                        RadioButton(
////                            selected = (text == selectedOption),
////                            onClick = {
////                                selectedOption = text
////                                callback(text)  // Pass the selected value to the callback
////                            }
////                        )
//                        Checkbox(
//                            checked = (text == selectedOption),
//                            onCheckedChange = {
//                                selectedOption = text
//                                callback(text)  // Pass the selected value to the callback
//                            }
//                        )}
//                        Text(
//                            text = text,
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier.padding(start = 16.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }

