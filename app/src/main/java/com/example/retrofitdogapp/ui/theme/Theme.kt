package com.example.retrofitdogapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AppPrimaryColor,
    secondary = ButtonColor,
    onPrimary = ButtonTextColor,
    background = Color.Black,
    surface = Color.DarkGray,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = AppPrimaryColor,
    secondary = ButtonColor,
    onPrimary = ButtonTextColor,
    background = BackgroundColor,
    surface = Color.White,
    onBackground = TextColor,
    onSurface = TextColor
)

@Composable
fun RetroFitDogAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}