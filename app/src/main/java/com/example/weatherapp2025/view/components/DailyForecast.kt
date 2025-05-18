package com.example.weatherapp2025.view.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.weatherapp2025.R
import com.example.weatherapp2025.ui.theme.ColorGradient1
import com.example.weatherapp2025.ui.theme.ColorGradient2
import com.example.weatherapp2025.ui.theme.ColorGradient3
import com.example.weatherapp2025.ui.theme.ColorTextSecondary
import com.example.weatherapp2025.ui.theme.ColorTextSecondaryVariant
import com.example.weatherapp2025.ui.theme.ColorWindForecast

@Composable
fun DailyForecast(
    modifier: Modifier = Modifier,
    forecast: String,
    date: String,
    degree: String,
    description: String,
    iconCode: String
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (forecastImage, forecastValue, windImage, title, descriptionRef, background) = createRefs()

        CardBackground(
            modifier = Modifier.constrainAs(background) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = descriptionRef.bottom,
                    topMargin = 24.dp
                )
                height = Dimension.fillToConstraints
            }
        )

        val imageUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"

        // Loading image using Coil
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Weather Icon",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(175.dp)
                .constrainAs(forecastImage) {
                    start.linkTo(parent.start, margin = 4.dp)
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = forecast,
            style = MaterialTheme.typography.titleLarge,
            color = ColorTextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start, margin = 24.dp)
                top.linkTo(forecastImage.bottom)
            }
        )

        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant,
            modifier = Modifier
                .constrainAs(descriptionRef) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom)
                }
                .padding(bottom = 24.dp)
        )

        ForecastValue(
            modifier = Modifier.constrainAs(forecastValue) {
                end.linkTo(parent.end, margin = 24.dp)
                top.linkTo(forecastImage.top)
                bottom.linkTo(forecastImage.bottom)
            },
            degree = degree,
            description = description
        )

        WindForecastImage(
            modifier = Modifier.constrainAs(windImage) {
                top.linkTo(title.top)
                bottom.linkTo(title.bottom)
                end.linkTo(parent.end, margin = 24.dp)
            }
        )
    }
}

@Composable
private fun CardBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.5f to ColorGradient2,
                    1f to ColorGradient3
                ),
                shape = RoundedCornerShape(32.dp)
            )
    )
}

@Composable
private fun ForecastValue(
    modifier: Modifier = Modifier,
    degree: String,
    description: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = degree,
                    letterSpacing = 0.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            0f to Color.White,
                            1f to Color.White.copy(alpha = 0.3f)
                        ),
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Black
                    ),
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(
                    text = "°",
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            0f to Color.White,
                            1f to Color.White.copy(alpha = 0.3f)
                        ),
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Light,
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant
        )
    }
}

@Composable
private fun WindForecastImage(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_frosty),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = ColorWindForecast
        )
        Icon(
            painter = painterResource(R.drawable.ic_wind),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = ColorWindForecast
        )
    }
}