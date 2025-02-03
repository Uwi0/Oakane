package com.kakapo.oakane.presentation.feature.wallet.component.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterLogSheetView(sheetState: SheetState, onDismiss: () -> Unit) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            FilterLogSheetContentView()
            ApplyFilterButtonView()
        }

    }
}

@Composable
private fun FilterLogSheetContentView() {
    Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Filter Log", style = MaterialTheme.typography.headlineSmall)
        FilterLogTopContentView()
        HorizontalDivider()
        FilterLogBottomContentView()
    }
}

@Composable
private fun FilterLogTopContentView() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Time Range",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        FilterRadioButtonView(title = "Last Week", isSelected = true, onClick = {})
        FilterRadioButtonView(title = "Last Month", isSelected = false, onClick = {})
        FilterRadioButtonView(title = "Custom", isSelected = false, onClick = {})
        FiltersCustomDateView()
    }
}

@Composable
private fun FilterLogBottomContentView() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Category",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        FilterRadioButtonView(title = "Transaction", isSelected = true, onClick = {})
        FilterRadioButtonView(title = "Goal Saving", isSelected = false, onClick = {})
        FilterRadioButtonView(title = "Wallet Transfer", isSelected = false, onClick = {})
    }
}

@Composable
private fun FilterRadioButtonView(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.labelLarge)
        RadioButton(selected = isSelected, onClick = onClick)
    }
}

@Composable
private fun FiltersCustomDateView() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        FilterCustomDateView(
            modifier = Modifier.weight(1f),
            title = "From",
            date = "2021-01-01",
            onClick = {}
        )
        FilterCustomDateView(
            modifier = Modifier.weight(1f),
            title = "To",
            date = "2021-12-31",
            onClick = {}
        )
    }
}

@Composable
private fun FilterCustomDateView(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.inverseOnSurface,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(

                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Icon(
                imageVector = Icons.Default.Event,
                contentDescription = null,

            )
        }
    }
}

@Composable
private fun ApplyFilterButtonView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            onClick = {},
            content = { Text("Apply Filter") }
        )
    }
}

@Composable
@Preview
private fun FilterLogSheetPreview() {
    AppTheme {
        Surface {
            FilterLogSheetContentView()
        }
    }
}