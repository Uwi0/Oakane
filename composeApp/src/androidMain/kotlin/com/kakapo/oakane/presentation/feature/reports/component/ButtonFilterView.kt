package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.viewModel.reports.ReportsEvent
import com.kakapo.oakane.presentation.viewModel.reports.ReportsState
import com.kakapo.oakane.presentation.viewModel.reports.model.MonthReport

@Composable
internal fun ButtonFilterView(uiState: ReportsState, onEvent: (ReportsEvent) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ButtonDateFilterView(uiState = uiState, onEvent = onEvent)
        ButtonWalletsDropDownMenu(uiState = uiState, onEvent = onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ButtonDateFilterView(uiState: ReportsState, onEvent: (ReportsEvent) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        ButtonFilterView(
            modifier = Modifier.menuAnchor(),
            title = uiState.selectedMonth.title,
            icon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) }
        )
        ExposedDropdownMenu(
            modifier = Modifier.heightIn(max = 360.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            MonthReport.entries.forEach { month ->
                DropdownMenuItem(
                    text = { Text(text = month.description) },
                    onClick = {
                        onEvent.invoke(ReportsEvent.FilterBy(month))
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ButtonFilterView(modifier: Modifier,title: String, icon: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.outline,
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
            Text(text = title)
            icon.invoke()
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
            title = uiState.selectedWalletName,
            icon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) }
        )

        ExposedDropdownMenu(
            modifier = Modifier.heightIn(max = 360.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            uiState.displayedWallets.forEach { wallet ->
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
    icon: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.outline,
        shadowElevation = 2.dp,
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