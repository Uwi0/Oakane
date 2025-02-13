package com.kakapo.oakane.presentation.feature.goal.component

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.goal.component.dialog.DialogAddSavingView
import com.kakapo.oakane.presentation.feature.goal.component.dialog.DeleteGoalDialogView
import com.kakapo.oakane.presentation.viewModel.goal.GoalDialogContent
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
            GoalDialogContent.UpdateAmount -> DialogAddSavingView(uiState = uiState, onEvent = onEvent)
            GoalDialogContent.DeleteGoal -> DeleteGoalDialogView(onEvent = onEvent)
        }

    }
}
