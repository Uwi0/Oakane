package com.kakapo.oakane.presentation.feature.wallet.component.sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterLogSheetView(
    sheetState: SheetState,
    filterState: FilterLogSheetState,
    onDismiss: () -> Unit = {}
) {

    if (filterState.isDialogShown) {
        CustomDatePickerDialog(
            initialValue = filterState.defaultValue,
            defaultValue = filterState.defaultValue,
            onDismiss = { filterState.isDialogShown = false },
            onConfirm = filterState::setFilterDate
        )
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            FilterLogSheetContentView(filterState)
            ApplyFilterButtonView(filterState)
        }

    }
}

@Composable
private fun FilterLogSheetContentView(state: FilterLogSheetState) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSheet(state)
        FilterLogTopContentView(state)
        HorizontalDivider()
        FilterLogBottomContentView(state)
    }
}

@Composable
private fun TitleSheet(state: FilterLogSheetState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Filter Log",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (state.isResetShown) {
            CustomTextButton(onClick = state::resetFilter) {
                Text(
                    text = "Reset",
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

    }
}

@Composable
private fun FilterLogTopContentView(state: FilterLogSheetState) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Time Range",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        FilterRadioButtonView(
            title = "Last Week",
            isSelected = state.isLastWeekSelected,
            onClick = state::onSelectedWeek
        )
        FilterRadioButtonView(
            title = "Last Month",
            isSelected = state.isLastMonthSelected,
            onClick = state::onSelectedMonth
        )
        FilterRadioButtonView(
            title = "Custom",
            isSelected = state.isCustomSelected,
            onClick = state::onSelectedCustom
        )
        FiltersCustomDateView(state)
    }
}

@Composable
private fun FilterLogBottomContentView(state: FilterLogSheetState) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Category",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        FilterRadioButtonView(
            title = "Transaction",
            isSelected = state.isTransactionSelected,
            onClick = state::onSelectedTransaction
        )
        FilterRadioButtonView(
            title = "Goal Saving",
            isSelected = state.isGoalSavingSelected,
            onClick = state::onSelectedGoalSaving
        )
        FilterRadioButtonView(
            title = "Wallet Transfer",
            isSelected = state.isTransferSelected,
            onClick = state::onSelectedTransfer
        )
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
private fun FiltersCustomDateView(state: FilterLogSheetState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        FilterCustomDateView(
            modifier = Modifier.weight(1f),
            title = "From",
            date = state.displayedStartDate,
            onClick = state::onShowDialogStartDate
        )
        FilterCustomDateView(
            modifier = Modifier.weight(1f),
            title = "To",
            date = state.displayedEndDate,
            onClick = state::onShowDialogEndDate
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
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
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
private fun ApplyFilterButtonView(state: FilterLogSheetState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            onClick = state::applyFilter,
            content = { Text("Apply Filter") }
        )
    }
}

@Composable
@Preview
private fun FilterLogSheetPreview() {
    AppTheme {
        Surface {
            FilterLogSheetContentView(state = rememberFilterLogSheetState())
        }
    }
}