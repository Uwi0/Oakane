package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedIconCircleButton
import com.kakapo.oakane.presentation.ui.component.RowWrapper

data class GoalContent(
    val icon: ImageVector,
    val title: String,
    val description: String
)

@Composable
internal fun GoalHeaderView(isVisible: Boolean = true, onAddItem: () -> Unit) {
    if (isVisible) {
        AddGoalView(onAddItem = onAddItem)
    } else {
        SimplifiedAddGoalView(onAddItem = onAddItem)
    }
}

@Composable
private fun AddGoalView(onAddItem: () -> Unit) {
    val content = defaultGoalContent()
    RowWrapper {
        CustomOutlinedIconCircleButton(icon = content.icon, onClick = {})
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = content.title, style = MaterialTheme.typography.titleMedium)
            Text(text = content.description)
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomIconButton(icon = Icons.Default.Add, onClick = onAddItem)
    }
}

@Composable
private fun SimplifiedAddGoalView(onAddItem: () -> Unit) {
    val content = defaultGoalContent()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = content.title, style = MaterialTheme.typography.bodyMedium)
        Icon(
            modifier = Modifier.clickable { onAddItem.invoke() },
            imageVector = Icons.Default.Add,
            contentDescription = ""
        )
    }
}

private fun defaultGoalContent() = GoalContent(
    icon = Icons.Outlined.WorkspacePremium,
    title = "My Goals",
    description = "Add New Goals"
)