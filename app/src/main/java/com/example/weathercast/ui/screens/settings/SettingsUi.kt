package com.example.weathercast.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercast.data.models.SettingOption
import com.example.weathercast.ui.theme.MyDarkBlue
import com.example.weathercast.ui.theme.MyLightBlue
import com.example.weathercast.ui.theme.Primary
import com.example.weathercast.utlis.Constants
import com.example.weathercast.utlis.restartActivity


@Composable
fun SettingsScreenMain(settingsViewModel: SettingsViewModel, navigateToMap: () -> Unit) {
    val context = LocalContext.current.applicationContext

    val settingsList = listOf(
        SettingOption("Language", listOf("English", "عربي", "Default")) { selected ->
            val language = when (selected) {
                "عربي" -> Constants.ARABIC_PARM
                else -> Constants.Emglish_PARM
            }
            settingsViewModel.setLanguage(language)
            restartActivity(context)
            Log.i("TAG", "SettingsScreenMain: Selected language $selected")
        },

        SettingOption("Temperature Unit", listOf("Celsius", "Fahrenheit", "Kelvin")) { selected ->
            val unit = when (selected) {
                "Celsius" -> Constants.CELSIUS_PARM
                "Fahrenheit" -> Constants.FAHRENHEIT_PARM
                else -> Constants.KELVIN_PARM
            }
            settingsViewModel.setTemperatureUnit(unit)
            Log.i("TAG", "SettingsScreenMain: Selected temperature unit $selected")
        },

        SettingOption("Location", listOf(Constants.GPS_TYPE, Constants.MAP_TYPE)) { selected ->
            settingsViewModel.setLocationType(selected)
            if (selected == Constants.MAP_TYPE) navigateToMap()
            Log.i("TAG", "SettingsScreenMain: Selected location $selected")
        },

        SettingOption("Wind Speed Unit", listOf("meter/sec", "mile/hour")) { selected ->
            val unit = when (selected) {
                "meter/sec" -> Constants.WIND_MITER_SEC
                "mile/hour" -> Constants.WIND_MILE_Hour
                else -> Constants.WIND_MITER_SEC
            }
            settingsViewModel.setTemperatureUnit(unit)
            Log.i("TAG", "SettingsScreenMain: Selected wind speed unit $selected")
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            "Preferences",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 25.sp, color = Primary
                        )
                    }

                    items(settingsList) { setting ->
                        RadioButtonSingleSelection(setting)
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        )
    }
}

@Composable
fun RadioButtonSingleSelection(setting: SettingOption) {
    var selectedOption by remember { mutableStateOf(setting.options[0]) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MyLightBlue,   // Light blue
                            MyDarkBlue  // Dark blue
                        )
                    )
                )

        )
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = setting.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black
                )

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                setting.options.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    selectedOption = text
                                    setting.onSelection(text)
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
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

