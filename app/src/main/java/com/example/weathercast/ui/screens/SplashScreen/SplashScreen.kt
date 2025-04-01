package com.example.weathercast.ui.screens.SplashScreen

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weathercast.R
import com.example.weathercast.ui.theme.Primary

@Composable
fun SplashScreen(navigationToHome: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animationweathersplash))
    var scale by remember { mutableFloatStateOf(0.8f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .fillMaxSize(.7f)
                .padding(bottom = 150.dp),
        )

        LaunchedEffect(Unit) {
            animate(
                initialValue = 0.5f,
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 1500)
            ) { value, _ ->
                scale = value
            }
            navigationToHome()
        }

        Text(
            text = "Weather Cast",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .scale(scale),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.White
        )
    }
}
