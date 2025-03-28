package com.kakapo.oakane.presentation.feature.reminder.component.dialog

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.viewModel.reminder.ReminderEvent
import com.kakapo.oakane.presentation.viewModel.reminder.ReminderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReminderDialog(state: ReminderState, onEvent: (ReminderEvent) -> Unit) {
    BasicAlertDialog(onDismissRequest = { onEvent.invoke(ReminderEvent.Dialog(shown = false)) }) {
        Surface(shape = MaterialTheme.shapes.large) {
            SetAlarmReminderDialogContent(
                state = state,
                onCancel = { onEvent.invoke(ReminderEvent.Dialog(shown = false)) },
                onConfirm = { hour, minute ->
                    onEvent.invoke(ReminderEvent.TimeSelected(hour, minute))
                }
            )
        }
    }
}