package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePicker
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterDateView(onEvent: (TransactionsEvent) -> Unit) {
    val datePickerState = rememberDatePickerState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomDatePicker(state = datePickerState)
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            onClick = {
                val timeMillis = datePickerState.selectedDateMillis ?: 0
                onEvent.invoke(TransactionsEvent.FilterByDate(timeMillis))
            },
            content = {
                Text("Apply Filter Date")
            }
        )
    }
}