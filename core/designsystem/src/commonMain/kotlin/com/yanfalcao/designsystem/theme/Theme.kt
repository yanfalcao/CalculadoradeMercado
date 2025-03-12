package com.yanfalcao.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal val DarkColorScheme =
    darkColorScheme(
        primary = Teal,
        onPrimary = Color.White,
        secondary = Amber,
        onSecondary = Color.Black,
        background = Gray900,
        onBackground = Color.White,
        surface = Gray800,
        onSurface = Color.White,
        surfaceVariant = Gray700,
        surfaceTint = Gray500,
        outline = Gray400,
        error = MaximumRed,
        onError = Color.White,
        scrim = Gray600Transparent40,
    )

internal val LightColorScheme =
    lightColorScheme(
        primary = Teal,
        onPrimary = Color.White,
        secondary = Amber,
        onSecondary = Color.Black,
        background = Color.White,
        onBackground = Color.Black,
        surface = Gray100,
        onSurface = Color.Black,
        surfaceVariant = Gray100,
        surfaceTint = Gray500,
        outline = Gray600,
        error = MaximumRed,
        onError = Color.White,
        scrim = BlackTransparent30,
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography(),
        content = content,
    )
}