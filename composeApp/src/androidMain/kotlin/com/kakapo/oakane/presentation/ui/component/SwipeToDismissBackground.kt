package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteBackgroundView(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    val alignment = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        SwipeToDismissBoxValue.Settled -> Alignment.CenterStart
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp),
        contentAlignment = alignment
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            when (dismissState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    DeleteText()
                    DeleteIcon()
                }

                else -> {
                    DeleteIcon()
                    DeleteText()
                }
            }
        }
    }
}

@Composable
private fun DeleteText() {
    Text(text = "Delete", color = MaterialTheme.colorScheme.onError)
}

@Composable
private fun DeleteIcon() {
    Icon(
        imageVector = Icons.Outlined.Delete,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SwipeToDeleteBackgroundPrev() {
    AppTheme {
        val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.EndToStart
        )
        SwipeToDeleteBackgroundView(dismissState = swipeToDismissBoxState)
    }
}