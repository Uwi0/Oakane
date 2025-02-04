package com.kakapo.oakane.presentation.ui.component.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    initialValue: Long? = System.currentTimeMillis(),
    defaultValue: Long = System.currentTimeMillis(),
    onDismiss: () -> Unit = {},
    onConfirm: (Long) -> Unit
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = initialValue)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            CustomTextButton(onClick = {
                val selectedDate = state.selectedDateMillis ?: defaultValue
                onConfirm.invoke(selectedDate)
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            CustomTextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        CustomDatePicker(state)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePicker(state: DatePickerState) {
    DatePicker(
        state = state,
        showModeToggle = false,
        title = null,
        headline = null,
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface)
    )
}

@Composable
@Preview
private fun CustomDatePickerPreview() {
    CustomDatePickerDialog {  }
}