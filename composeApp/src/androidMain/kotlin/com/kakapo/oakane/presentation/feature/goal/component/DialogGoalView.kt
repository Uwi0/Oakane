package com.kakapo.oakane.presentation.feature.goal.component

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.goal.component.dialog.DialogAddSavingView
import com.kakapo.oakane.presentation.feature.goal.component.dialog.DialogDeleteGoalView
import com.kakapo.oakane.presentation.viewModel.goal.DialogContent
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DialogGoalView(
    uiState: GoalState,
    onEvent: (GoalEvent) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(GoalEvent.Dialog(false)) }
    ) {
        when (uiState.dialogContent) {
            DialogContent.UpdateAmount -> DialogAddSavingView(uiState = uiState, onEvent = onEvent)
            DialogContent.DeleteGoal -> DialogDeleteGoalView(onEvent = onEvent)
        }

    }
}
