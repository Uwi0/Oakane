package com.kakapo.oakane.presentation.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedIconCircleButton
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.viewModel.home.HomeEvent

@Composable
internal fun MonthlyBudgetView(onEvent: (HomeEvent) -> Unit) {
    ColumnWrapper(modifier = Modifier.padding(12.dp)) {
        MonthlyBudgetContent(onEvent = onEvent)
        HorizontalDivider()
        IncomeAndExpenseContent()
    }
}

@Composable
private fun MonthlyBudgetContent(onEvent: (HomeEvent) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomOutlinedIconCircleButton(icon = Icons.Outlined.Payments) { }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            BudgetHeader(onNavigateToAddBudget = { onEvent.invoke(HomeEvent.ToMonthlyBudget) })
            Text(text = "Rp. 0", style = MaterialTheme.typography.titleMedium)
            CustomProgressIndicatorView(value = 0.5f)
            SupportContent(
                spent = 0.0,
                left = 0.0
            )
        }
    }
}

@Composable
private fun BudgetHeader(onNavigateToAddBudget: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Monthly Budget",
            style = MaterialTheme.typography.titleMedium
        )
        CustomIconButton(
            modifier = Modifier.size(24.dp),
            icon = Icons.Default.Edit,
            onClick = { onNavigateToAddBudget.invoke() }
        )
    }
}


@Composable
private fun IncomeAndExpenseContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BalanceItemView(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            balance = 0.0,
            type = TransactionType.Income
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        BalanceItemView(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            balance = 0.0,
            type = TransactionType.Expense
        )
    }
}

@Composable
private fun SupportContent(spent: Double, left: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Spent Rp.0",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Left Rp.0",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}