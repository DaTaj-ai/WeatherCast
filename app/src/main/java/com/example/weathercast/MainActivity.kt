package com.example.weathercast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weathercast.navigation.NavSetup
import com.example.weathercast.navigation.ScreenRoute
import com.example.weathercast.ui.screens.home.DailyForecast
import com.example.weathercast.ui.screens.home.WeeklyForecast
import com.example.weathercast.ui.screens.home.components.AirQuality
import com.example.weathercast.ui.theme.WeatherCastTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedIndex by remember { mutableStateOf(0) }
            val navigationBarItems = remember { NavigationBarItem.values() }
            val navController = rememberNavController()

            MainContent(
                selectedIndex = selectedIndex,
                onItemClick = { index -> selectedIndex = index },
                navigationBarItems = navigationBarItems.toList(),
                navController
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    navigationBarItems: List<NavigationBarItem>, navController: NavHostController
) {
    AnimatedNavigationBar(
        modifier = Modifier.height(64.dp),
        selectedIndex = selectedIndex,
        cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
        ballAnimation = Parabolic(tween(300)),
        indentAnimation = Height(tween(300)),
        barColor = MaterialTheme.colorScheme.background,
        ballColor = MaterialTheme.colorScheme.primary
    ) {
        navigationBarItems.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable { onItemClick(item.ordinal) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            navController.navigate(item.route)
                        },
                    imageVector = item.icon,
                    contentDescription = "Bottom bar icon",
                    tint = if (selectedIndex == item.ordinal) {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun MainContent(
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    navigationBarItems: List<NavigationBarItem>,
    navController: NavHostController
) {
    WeatherCastTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppBar {
                    BottomNavigationBar(
                        selectedIndex = selectedIndex,
                        onItemClick = onItemClick,
                        navigationBarItems = navigationBarItems,
                        navController
                    )
                }
            }
        ) { innerPadding ->
            NavSetup(navController)
        }
    }
}

enum class NavigationBarItem(val icon: ImageVector, val route: ScreenRoute) {
    Home(Icons.Filled.Home, ScreenRoute.HomeScreen),
    Alarm(Icons.Filled.Alarm, ScreenRoute.AlarmRoute),
    Favorite(Icons.Filled.Favorite, ScreenRoute.FavoriteRoute),
    Settings(Icons.Filled.Settings, ScreenRoute.SettingsRoute)
}


fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}





