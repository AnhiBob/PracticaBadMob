package com.example.practicabad.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF48B2E7),
    secondary = Color(0xFF2B6B8B),
    surface = Color(0xFFFFFFFF),
    background = Color(0xFFF7F7F9),
    onBackground = Color(0xFF6A6A6A),
    onSurface = Color(0xFF2B2B2B),
    onPrimary = Color.White,
    error = Color(0xFFF87265)
)

@Composable
fun PracticaBadTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme() else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}