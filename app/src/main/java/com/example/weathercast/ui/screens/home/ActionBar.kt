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
    modifier: Modifier = Modifier, location: String , dateString: String = "Today"
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LocationInfo(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            location = location , dateString
        )

    }
}


@Composable
private fun LocationInfo(
    modifier: Modifier = Modifier,
    location: String, dateString: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                androidx.compose.material3.Icon(Icons.Filled.LocationOn, contentDescription = "")
                Text(
                    text = location,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0, 0, 0),
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = dateString ,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0, 0, 0),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//@Composable
//private fun ProgressBar(
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .background(
//                brush = Brush.linearGradient(
//                    0f to ColorGradient1,
//                    0.25f to ColorGradient2,
//                    1f to ColorGradient3
//                ),
//                shape = RoundedCornerShape(8.dp)
//            )
//            .padding(
//                vertical = 2.dp,
//                horizontal = 10.dp
//            )
//    ) {
//        Text(
//            text = "Updating •",
//            style = MaterialTheme.typography.labelSmall,
//            color = ColorTextSecondary.copy(alpha = 0.7f)
//        )
//    }
//}