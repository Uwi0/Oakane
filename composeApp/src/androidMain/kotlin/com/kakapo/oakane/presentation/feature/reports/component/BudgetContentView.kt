package com.kakapo.oakane.presentation.feature.reports.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kakapo.model.Currency
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import com.kakapo.model.toFormatCurrency
import com.kakapo.oakane.presentation.designSystem.component.progressIndicator.CustomProgressIndicatorView
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun BudgetContentView(item: MonthlyBudgetOverView) {
    val currency = item.currency
    val limit = item.limit.toFormatCurrency(currency)
    val spent = item.spent.toFormatCurrency(currency)
    ColumnWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = "Budget: $limit",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        CustomProgressIndicatorView(item.progress)
        Text(
            text = "Spent: $spent)}",
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

@Composable
private fun BudgetItemView(amount: Double, isExpense: Boolean, currency: Currency) {
    val icon = if (isExpense) Icons.Default.ArrowDownward
    else Icons.Default.ArrowUpward

    val color = if (isExpense) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.primary

    val title = if (isExpense) "Total Expense"
    else "Total Income"

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconView(icon = icon, color = color)
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.outline
            )
            Text(amount.toFormatCurrency(currency), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun CircleIconView(icon: ImageVector, color: Color) {
    Surface(color = color, shape = CircleShape) {
        Icon(modifier = Modifier.padding(4.dp), imageVector = icon, contentDescription = "")
    }
}