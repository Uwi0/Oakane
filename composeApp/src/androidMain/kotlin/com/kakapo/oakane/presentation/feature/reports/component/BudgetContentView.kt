package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.ui.component.wallet.BudgetItemView
import com.kakapo.oakane.presentation.viewModel.reports.ReportsState

@Composable
internal fun BudgetContentView(uiState: ReportsState) {
    val item = uiState.monthlyOverView

    ColumnWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        theme = uiState.theme
    ) {
        Text(
            text = "Budget: ${uiState.limit}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        CustomProgressIndicatorView(item.progress)
        Text(
            text = "Spent: ${uiState.spent})}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
        HorizontalDivider()
        BottomContentView(item = item)
    }
}

@Composable
private fun BottomContentView(item: MonthlyBudgetOverView) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BudgetItemView(amount = item.totalIncome, isExpense = false, currency = item.currency)
        VerticalDivider(Modifier.height(48.dp))
        BudgetItemView(amount = item.totalExpense, isExpense = true, currency = item.currency)
    }
}