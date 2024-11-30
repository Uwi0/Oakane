package com.kakapo.oakane.presentation.feature.goal.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent

@Composable
internal fun DialogDeleteGoalView(onEvent: (GoalEvent) -> Unit) {
    Surface(shape = MaterialTheme.shapes.medium) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Are you sure want to delete this goal?",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("This action can't be undone", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.weight(1f))
                CustomTextButton(
                    onClick = { onEvent.invoke(GoalEvent.Dialog(false)) },
                    content = { Text(text = "Cancel") }
                )
                CustomButton(
                    onClick = { onEvent.invoke(GoalEvent.DeleteGoal) },
                    containerColor = MaterialTheme.colorScheme.error,
                    content = { Text(text = "Delete") }
                )
            }
        }
    }
}
