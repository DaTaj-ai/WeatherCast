package com.example.weathercast.ui.screens.settings

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
        SettingOption(
            title = "Language",
            options = listOf("English", "عربي", "Default"),
            { selected ->
                val language = when (selected) {
                    "عربي" -> Constants.ARABIC_PARM
                    else -> Constants.Emglish_PARM
                }
                settingsViewModel.setLanguage(language)
                restartActivity(context)
                Log.i("TAG", "SettingsScreenMain: Selected language $selected")
            },
            icon = Icons.Default.Language
        ),

        SettingOption(
            title = "Temperature Unit",
            options = listOf("Celsius", "Fahrenheit", "Kelvin"),
            { selected ->
                val unit = when (selected) {
                    "Celsius" -> Constants.CELSIUS_PARM
                    "Fahrenheit" -> Constants.FAHRENHEIT_PARM
                    else -> Constants.KELVIN_PARM
                }
                settingsViewModel.setTemperatureUnit(unit)
                Log.i("TAG", "SettingsScreenMain: Selected temperature unit $selected")
            },
            icon = Icons.Default.Thermostat
        ),

        SettingOption(
            title = "Wind Speed Unit",
            options = listOf("meter/sec", "mile/hour"),
            onSelection = { selected ->
                val unit = when (selected) {
                    "meter/sec" -> Constants.WIND_MITER_SEC
                    "mile/hour" -> Constants.WIND_MILE_Hour
                    else -> Constants.WIND_MITER_SEC
                }
                settingsViewModel.setTemperatureUnit(unit)
                Log.i("TAG", "SettingsScreenMain: Selected wind speed unit $selected")
            },
            icon = Icons.Default.Air
        ),
                SettingOption(
                title = "Location",
        options = listOf(Constants.GPS_TYPE, Constants.MAP_TYPE),
        onSelection = { selected ->
            settingsViewModel.setLocationType(selected)
            if (selected == Constants.MAP_TYPE) navigateToMap()
            Log.i("TAG", "SettingsScreenMain: Selected location $selected")
        },
        icon = Icons.Default.LocationOn
    )
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
                            MyLightBlue,
                            MyDarkBlue
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Title Row with Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = setting.icon,
                        contentDescription = "Setting icon",
                        modifier = Modifier.size(24.dp),
                        tint = Primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = setting.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Primary
                    )
                }

                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Horizontal Options Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    setting.options.forEach { text ->
                        Row(
                            modifier = Modifier
//                                .weight(.5f)
                                .heightIn(min = 48.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        selectedOption = text
                                        setting.onSelection(text)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 4.dp),
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold

                            )
                        }
                    }
                }
            }
        }
    }
}
//
//@Composable
//fun RadioButtonSingleSelection(setting: SettingOption) {
//    var selectedOption by remember { mutableStateOf(setting.options[0]) }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp, horizontal = 16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        shape = MaterialTheme.shapes.medium,
//        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MyLightBlue,
//                            MyDarkBlue
//                        )
//                    )
//                )
//        )
//        {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row {
//
//                    Text(
//                        text = setting.title,
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(bottom = 12.dp),
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold, color = Color.Black
//                    )
//                    Spacer(modifier = Modifier.padding(4.dp))
//                    Icon(
//                        modifier = Modifier
//                            .size(30.dp),
//                        imageVector = setting.icon,
//                        contentDescription = "Bottom bar icon",
//                    )
//                }
//
//                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
//
//                setting.options.forEach { text ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(min = 56.dp)
//                            .selectable(
//                                selected = (text == selectedOption),
//                                onClick = {
//                                    selectedOption = text
//                                    setting.onSelection(text)
//                                },
//                                role = Role.RadioButton
//                            )
//                            .padding(horizontal = 8.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RadioButton(
//                            selected = (text == selectedOption),
//                            onClick = null,
//                            colors = RadioButtonDefaults.colors(
//                                selectedColor = Primary,
//                                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
//                            )
//                        )
//                        Text(
//                            text = text,
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

