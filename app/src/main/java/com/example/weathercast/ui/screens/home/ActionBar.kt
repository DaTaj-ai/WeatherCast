package com.example.weathercast.ui.screens.home

//import androidlead.weatherappui.R
//import androidlead.weatherappui.ui.theme.ColorGradient1
//import androidlead.weatherappui.ui.theme.ColorGradient2
//import androidlead.weatherappui.ui.theme.ColorGradient3
//import androidlead.weatherappui.ui.theme.ColorImageShadow
//import androidlead.weatherappui.ui.theme.ColorSurface
//import androidlead.weatherappui.ui.theme.ColorTextPrimary
//import androidlead.weatherappui.ui.theme.ColorTextSecondary
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercast.ui.theme.ColorGradient1
import com.example.weathercast.ui.theme.ColorGradient2
import com.example.weathercast.ui.theme.ColorGradient3
import com.example.weathercast.ui.theme.ColorTextSecondary

//@Preview(showBackground = true)
//@Composable
//fun HomeTest () {
////    com.example.weathercast.ui.screens.V2home.ActionBar(location = "Ahmedabad")
//}

@Composable
fun Header(
    modifier: Modifier = Modifier , location: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LocationInfo(
            modifier = Modifier.padding(top = 10.dp),
            location = location
        )

    }
}


@Composable
private fun LocationInfo(
    modifier: Modifier = Modifier,
    location: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Icon(Icons.Filled.LocationOn, contentDescription = "")
            Text(
                text = location,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0,0,0),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun ProgressBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.25f to ColorGradient2,
                    1f to ColorGradient3
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                vertical = 2.dp,
                horizontal = 10.dp
            )
    ) {
        Text(
            text = "Updating •",
            style = MaterialTheme.typography.labelSmall,
            color = ColorTextSecondary.copy(alpha = 0.7f)
        )
    }
}