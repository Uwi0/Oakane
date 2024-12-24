package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
internal fun ButtonFilterView() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ButtonView(title = "Jan", icon = Icons.Default.CalendarMonth, onCLick = {})
        ButtonView(title = "My Wallet", icon = Icons.Default.ArrowDropDown, onCLick = {})
    }
}

@Composable
private fun ButtonView(title: String, icon: ImageVector, onCLick: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.outline,
        shadowElevation = 2.dp,
        onClick = {}
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title)
            Icon(imageVector = icon, contentDescription = null)
        }
    }
}