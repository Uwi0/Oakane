package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.transaction.TransactionType

@Composable
fun TransactionTypeIcon(modifier: Modifier = Modifier, iconSize: Dp = 48.dp, type: TransactionType) {
    val (icon, backgroundColor) = transactionColor(type)
    Surface(modifier = modifier, shape = CircleShape, color = backgroundColor) {
        Box(modifier = Modifier.size(iconSize)) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = icon,
                contentDescription = "",
            )
        }
    }
}

fun transactionColor(type: TransactionType): Pair<ImageVector, Color> {
    val icon = if (type == TransactionType.Income) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
    val color = if (type == TransactionType.Income) Color.Green else Color.Red
    return Pair(icon, color)
}