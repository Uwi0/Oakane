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
import com.kakapo.common.toFormatIDR
import com.kakapo.common.toFormatIDRWithCurrency
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedIconCircleButton
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.viewModel.home.HomeEvent
import com.kakapo.oakane.presentation.viewModel.home.HomeState

@Composable
internal fun MonthlyBudgetView(uiState: HomeState, onEvent: (HomeEvent) -> Unit) {
    ColumnWrapper(modifier = Modifier.padding(12.dp)) {
        MonthlyBudgetContent(overView = uiState.monthlyBudgetOverView, onEvent = onEvent)
        HorizontalDivider()
        IncomeAndExpenseContent(overView = uiState.monthlyBudgetOverView)
    }
}

@Composable
private fun MonthlyBudgetContent(
    overView: MonthlyBudgetOverViewModel,
    onEvent: (HomeEvent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomOutlinedIconCircleButton(icon = Icons.Outlined.Payments) { }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            BudgetHeader(onNavigateToAddBudget = { onEvent.invoke(HomeEvent.ToMonthlyBudget) })
            Text(
                text = overView.limit.toFormatIDRWithCurrency(),
                style = MaterialTheme.typography.titleMedium
            )
            CustomProgressIndicatorView(value = overView.progress)
            SupportContent(
                spent = overView.spent,
                left = overView.left
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
private fun IncomeAndExpenseContent(overView: MonthlyBudgetOverViewModel) {
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
            balance = overView.totalIncome,
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
            balance = overView.totalExpense,
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
            text = "Spent Rp ${spent.toFormatIDR()}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Left Rp ${left.toFormatIDR()}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}