package com.kakapo.oakane.presentation.designSystem.component.progressIndicator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressIndicatorView(value: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )
    LinearProgressIndicator(
        progress = {
            animatedProgress
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(CircleShape),
    )
}