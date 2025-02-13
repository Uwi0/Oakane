package com.kakapo.oakane.presentation.feature.goal.component.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.ui.component.dialog.DeleteContentDialogView
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent

@Composable
internal fun DeleteGoalDialogView(onEvent: (GoalEvent) -> Unit) {
    Surface(shape = MaterialTheme.shapes.medium) {
        DeleteContentDialogView(
            title = "Are you sure want to delete this goal?",
            subtitle = "This action can't be undone",
            onDismiss = { onEvent.invoke(GoalEvent.Dialog(false)) },
            onConfirm = { onEvent.invoke(GoalEvent.DeleteGoal) }
        )
    }
}
