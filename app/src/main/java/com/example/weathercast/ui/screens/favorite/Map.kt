package com.example.weathercast.ui.screens.favorite

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import org.checkerframework.common.subtyping.qual.Bottom


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Preview
    @Composable
    fun ShowGoogleMap() {
        val singapore = LatLng(1.35, 103.87)
        val singaporeMarkerState = rememberMarkerState(position = singapore)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }

        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.BottomCenter) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = singaporeMarkerState,
                    title = "Singapore",
                    snippet = "Marker in Singapore",
                    draggable = true
                )
            }

            // Floating button positioned at the bottom right
            Button(
                onClick = { Log.i("TAG", "ShowGoogleMap: Button clicked") },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding( bottom = 100.dp)
                , colors =buttonColors(containerColor = Color.Black),
            ) {
                Text("Select".uppercase(), fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

    }


