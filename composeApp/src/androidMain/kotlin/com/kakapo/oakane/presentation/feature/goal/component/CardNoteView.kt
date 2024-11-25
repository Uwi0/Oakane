package com.kakapo.oakane.presentation.feature.goal.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.viewModel.goal.GoalState

@Composable
internal fun CardNoteView(uiState: GoalState) {
    ColumnWrapper {
        Text(text = "Note", style = MaterialTheme.typography.titleMedium)
        Text(text = uiState.note)
    }
}