package com.example.weathercast.ui.screens.settings

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.restartActivity

@Composable
fun SettingsScreenMain(navigateToMap: () -> Unit) {
    var context = LocalContext.current.applicationContext

    // Shared preferences (will be replaced by a data source)
    val shardPreferences = context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE)

    val languageMap = mapOf(
        "Language" to Pair(listOf("English", "عربي", "Default")) { selected: String ->
            if (selected == "عربي") {
                shardPreferences.edit().putString(Constants.LANGUAGE, Constants.ARABIC_PARM).apply()

            }else if (selected == "Default") {
                shardPreferences.edit().putString(Constants.LANGUAGE, Constants.Emglish_PARM).apply()
            }
            else {
                shardPreferences.edit().putString(Constants.LANGUAGE, Constants.Emglish_PARM).apply()
            }
            restartActivity(context)
            Log.i("TAG", "SettingsScreenMain: Selected language $selected")
        }
    )

    val tempUnitMap = mapOf(
        "Temperature Unit" to Pair(listOf("Celsius", "Fahrenheit", "Kelvin")) { selected: String ->
            if (selected == "Celsius") {
                shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, Constants.CELSIUS_PARM).apply()
            }
            else if (selected == "Fahrenheit") {
                shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, Constants.FAHRENHEIT_PARM).apply()
            }
            else {
                shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, Constants.KELVIN_PARM).apply()
            }
            Log.i("TAG", "SettingsScreenMain: Selected temperature unit $selected")
        }
    )

    val locationMap = mapOf(
        "Location" to Pair(listOf(Constants.GPS_TYPE, Constants.MAP_TYPE)) { selected: String ->

            shardPreferences.edit().putString(Constants.LOCATION_TYPE, selected).apply()

            if (selected == Constants.MAP_TYPE) {
                navigateToMap()
            }

            Log.i("TAG", "SettingsScreenMain: Selected location $selected")
        }
    )

    val windSpeedUnitMap = mapOf(
        "Wind Speed Unit" to Pair(listOf("meter/sec", "mile/hour")) { selected: String ->
            shardPreferences.edit().putString(Constants.TEMPERATURE_UNIT, selected).apply()
            Log.i("TAG", "SettingsScreenMain: Selected wind speed unit $selected")
        }
    )

    // List of all settings maps
    val list = listOf(languageMap, tempUnitMap, locationMap, windSpeedUnitMap)

    LazyColumn {
        items(list.size) { index ->
            val item = list[index]
            RadioButtonSingleSelection(item.entries.first())
        }
    }
}

@Composable
fun RadioButtonSingleSelection(data: Map.Entry<String, Pair<List<String>, (String) -> Int>>) {
    // Extract the data
    val (label, value) = data
    val (options, callback) = value

    var selectedOption by remember { mutableStateOf(options[0]) }

    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column() {
            Text(
                text = label,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                options.forEach { text ->
                    Column(
                        Modifier
                            .height(80.dp)
                            .align(Alignment.CenterVertically)
                            .padding(top = 16.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    selectedOption = text
                                    callback(text)
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                selectedOption = text
                                callback(text)  // Pass the selected value to the callback
                            }
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
