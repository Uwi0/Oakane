package com.kakapo.oakane.presentation.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.designSystem.component.textField.OutlinedCurrencyTextField
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DialogAddGoalSavingView(
    uiState: GoalState,
    onEvent: (GoalEvent) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(GoalEvent.Dialog(false)) }
    ) {
        DialogContentView(uiState = uiState, onEvent = onEvent)
    }
}

@Composable
private fun DialogContentView(uiState: GoalState, onEvent: (GoalEvent) -> Unit) {
    Surface(shape = MaterialTheme.shapes.medium) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "How much do you want to save?",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedCurrencyTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.savingAmount,
                placeHolder = "Saving Amount...",
                prefix = "Rp",
                onValueChange = { amount -> onEvent.invoke(GoalEvent.Change(amount))}
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.weight(1f))
                CustomTextButton(
                    onClick = { onEvent.invoke(GoalEvent.Dialog(false)) },
                    content = { Text(text = "Cancel") }
                )
                CustomButton(
                    onClick = { onEvent.invoke(GoalEvent.AddSaving)},
                    content = { Text(text = "Save") }
                )
            }
        }
    }
}

@Composable
@Preview
private fun DialogContentPreview() {
    AppTheme {
        val uiState = GoalState()
        DialogContentView(uiState = uiState, onEvent = {})
    }
}