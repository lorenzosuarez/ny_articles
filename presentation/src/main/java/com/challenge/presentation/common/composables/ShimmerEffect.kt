package com.challenge.presentation.common.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Displays a shimmer effect as a placeholder for loading content, allowing customization of the effect's shape.
 *
 * @param modifier Modifier to be applied to the shimmer effect. Can be used to specify size, padding, etc.
 * @param color The primary color to use for the shimmer effect. Defaults to the primary color from MaterialTheme.
 * @param shape The shape of the shimmer effect. Allows for rounded corners or other custom shapes.
 */
@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(0.dp),
) {
    val shimmerColors =
        listOf(
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.7f),
            color.copy(alpha = 0.3f),
        )

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "",
    )

    val brush =
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnim, y = translateAnim),
        )

    Box(
        modifier =
            modifier
                .background(brush = brush, shape = shape),
    )
}
