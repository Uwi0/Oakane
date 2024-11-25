package com.kakapo.oakane.presentation.feature.goal.component.card

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.viewModel.goal.GoalState

@Composable
internal fun CardNoteView(uiState: GoalState) {
    ColumnWrapper(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
        Text(text = "Note", style = MaterialTheme.typography.titleMedium)
        Text(text = uiState.goal.note)
    }
}