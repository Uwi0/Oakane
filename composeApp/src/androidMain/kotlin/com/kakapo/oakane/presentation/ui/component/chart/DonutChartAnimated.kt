package com.kakapo.oakane.presentation.ui.component.chart

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

private const val DividerLengthInDegrees = 0f
private const val DELAY_MILLIS = 600
private const val DURATION_MILLIS = 1000

@Composable
fun AnimatedDonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
){
    val currentState = remember{
        MutableTransitionState(AnimatedCircleProgress.START).apply {
            targetState = AnimatedCircleProgress.END
        }
    }
    val stroke = with(LocalDensity.current){ Stroke(48.dp.toPx()) }
    val transition = rememberTransition(currentState)
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = DELAY_MILLIS,
                durationMillis = DURATION_MILLIS,
                easing = LinearOutSlowInEasing
            )
        },
        label = "angleCircle"
    ){ progress ->
        if (progress == AnimatedCircleProgress.START) 0f
        else 360f
    }
    val shift by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = DELAY_MILLIS,
                durationMillis = DURATION_MILLIS,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
            )
        },
        label = "shiftCircle"
    ){ progress -> if(progress == AnimatedCircleProgress.START) 0f else 360f }

    Canvas(modifier = modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(
            width = innerRadius * 2,
            height = innerRadius * 2
        )
        var startAngle = shift - 90f
        proportions.forEachIndexed { index, proportion ->
            val sweep = proportion * angleOffset
            drawArc(
                color = colors[index],
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }
}

private enum class AnimatedCircleProgress{ START, END }