package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent

@Composable
internal fun LimitCategoryHeaderView(
    modifier: Modifier = Modifier,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Limited Category",
            style = MaterialTheme.typography.bodyLarge
        )
        CustomIconButton(
            icon = Icons.Default.Add,
            onClick = { onEvent.invoke(MonthlyBudgetEvent.Dialog(shown = true)) }
        )
    }
}
