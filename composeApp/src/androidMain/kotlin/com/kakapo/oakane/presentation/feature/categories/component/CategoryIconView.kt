package com.kakapo.oakane.presentation.feature.categories.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CategoryIconView(
    icon: Int,
    color: Color,
    onClick: () -> Unit = {}
) {
    val contentColor = if (color.luminance() < 0.5f) Color.White else Color.Black
    Surface(onClick = onClick, color = color, shape = CircleShape) {
        Icon(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = contentColor
        )
    }
}