package com.yanfalcao.designsystem.widget

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonLoadingWidget(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = shimmerBrush(),
                shape = RoundedCornerShape(size = 15.dp),
            ),
    )
}

@Composable
private fun shimmerBrush(): Brush {
    val colorSchema = MaterialTheme.colorScheme
    val shimmerColors = listOf(
        colorSchema.surface.copy(alpha = 0.6f),
        colorSchema.surface.copy(alpha = 0.2f),
        colorSchema.surface.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1300f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value),
    )
}