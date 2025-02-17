package com.kakapo.oakane.presentation.feature.goal.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.common.formatDateWith
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.viewModel.goal.GoalState

@Composable
internal fun CardTimeView(uiState: GoalState) {
    ColumnWrapper(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        theme = uiState.theme
    ) {
        TimeContentView(uiState = uiState)
        Text(
            text = "${uiState.dayLeft} Day Left",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun TimeContentView(uiState: GoalState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardContentWithIconView(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.CalendarToday,
            title = "Start Date",
            content = uiState.goal.startDate.formatDateWith(pattern = "dd MMM yyyy")
        )
        VerticalDivider(modifier = Modifier.height(30.dp))
        CardContentWithIconView(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Event,
            title = "End Date",
            content = uiState.goal.endDate.formatDateWith(pattern = "dd MMM yyyy")
        )
    }
}
