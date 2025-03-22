package com.example.weathercast

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weathercast.navigation.NavSetup
import com.example.weathercast.navigation.ScreenRoute
import com.example.weathercast.ui.theme.WeatherCastTheme
import com.example.weathercast.utlis.Constants
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationState by mutableStateOf<Location?>(null)
    private var locationNameState by mutableStateOf("Unknown")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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

    override fun onStart() {
        super.onStart()
        if (checkPermission()) {
            if (isLocationEnabled()) {
                getLocation()
            } else {
                enableLocationServices()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {

        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()

        locationCallback = object : LocationCallback() {
            @SuppressLint("SuspiciousIndentation", "CommitPrefEdits")
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    Log.i("Location", "yes we are here :  ${location.latitude}  ${location.longitude}")
                    var sp = getSharedPreferences(Constants.SETTINGS, MODE_PRIVATE)
                    sp.edit().putString(Constants.USER_LAT, location.latitude.toString()).commit()
                    sp.edit().putString(Constants.USER_LONG, location.longitude.toString()).commit()

                }
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableLocationServices() {
        Toast.makeText(this, "Turn on Location Services", Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onStop() {
        super.onStop()
        fusedLocationClient.removeLocationUpdates(locationCallback)
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
        cornerRadius = shapeCornerRadius(cornerRadius = 64.dp),
        ballAnimation = Parabolic(tween(300)),
        indentAnimation = Height(tween(300)),
        barColor = MaterialTheme.colorScheme.background,
        ballColor = MaterialTheme.colorScheme.primary
    ) {
        navigationBarItems.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        onItemClick(item.ordinal)
                        navController.navigate(item.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(26.dp),
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

@RequiresApi(Build.VERSION_CODES.O)
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
            },
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





