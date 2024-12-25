package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.viewModel.reports.ReportsEvent
import com.kakapo.oakane.presentation.viewModel.reports.ReportsState

@Composable
internal fun ButtonFilterView(uiState: ReportsState, onEvent: (ReportsEvent) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ButtonView(title = "Jan", icon = Icons.Default.CalendarMonth, onCLick = {})
        ButtonWalletsDropDownMenu(uiState = uiState, onEvent = onEvent)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ButtonWalletsDropDownMenu(uiState: ReportsState, onEvent: (ReportsEvent) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        ButtonWalletsView(
            modifier = Modifier.menuAnchor(),
            title = uiState.selectedWallet,
            icon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
            onCLick = { isExpanded = !isExpanded }
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = "All Wallet") },
                onClick = {
                    onEvent.invoke(ReportsEvent.SelectedAllWallet)
                    isExpanded = false
                }
            )
            uiState.wallets.forEach { wallet ->
                DropdownMenuItem(
                    text = { Text(text = wallet.name) },
                    onClick = {
                        onEvent.invoke(ReportsEvent.Selected(wallet))
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ButtonWalletsView(
    modifier: Modifier = Modifier,
    title: String,
    icon: @Composable () -> Unit,
    onCLick: () -> Unit
) {
    Surface(
        modifier = modifier,
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
            icon.invoke()
        }
    }
}