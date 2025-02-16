package com.kakapo.oakane.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.ui.util.cardTonalElevation

@Composable
fun ColumnWrapper(
    modifier: Modifier = Modifier,
    theme: Theme,
    shapes: Shape = MaterialTheme.shapes.medium,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = shapes,
        shadowElevation = 2.dp,
        tonalElevation = cardTonalElevation(theme),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content.invoke(this)
        }
    }
}

@Composable
fun RowWrapper(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement
        ) {
            content.invoke(this)
        }
    }
}

