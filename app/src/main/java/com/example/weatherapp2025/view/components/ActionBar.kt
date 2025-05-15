package com.example.weatherapp2025.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp2025.R
import com.example.weatherapp2025.ui.theme.ColorGradient1
import com.example.weatherapp2025.ui.theme.ColorGradient2
import com.example.weatherapp2025.ui.theme.ColorGradient3
import com.example.weatherapp2025.ui.theme.ColorImageShadow
import com.example.weatherapp2025.ui.theme.ColorSurface
import com.example.weatherapp2025.ui.theme.ColorTextPrimary
import com.example.weatherapp2025.ui.theme.ColorTextSecondary
import com.example.weatherapp2025.viewmodel.WeatherViewModel

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    location: String
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
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location_pin),
                    contentDescription = null,
                    modifier = Modifier.height(18.dp),
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}