package com.yanfalcao.designsystem.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun CustomGradient(size: Int, index: Int): Brush {
    val colors: MutableList<Color> = mutableListOf()
    val color1: Color = MaterialTheme.colorScheme.primary
    val color2: Color = MaterialTheme.colorScheme.secondary

    if(index == 0) {
        colors.add(color1)
    } else {
        val fraction = index.toFloat() / size
        colors.add(
            interpolateColor(color1, color2, fraction)
        )
    }

    if (index+1 == size) {
        colors.add(color2)
    } else {
        val fraction = (index+1).toFloat() / size
        colors.add(
            interpolateColor(color1, color2, fraction)
        )
    }

    return Brush.verticalGradient(colors)
}

@Composable
private fun interpolateColor(
    color1: Color,
    color2: Color,
    fraction: Float = 0.5f
): Color {
    val red = (color1.red + fraction * (color2.red - color1.red)).coerceIn(0f, 1f)
    val green = (color1.green + fraction * (color2.green - color1.green)).coerceIn(0f, 1f)
    val blue = (color1.blue + fraction * (color2.blue - color1.blue)).coerceIn(0f, 1f)
    val alpha = (color1.alpha + fraction * (color2.alpha - color1.alpha)).coerceIn(0f, 1f)
    return Color(red, green, blue, alpha)
}