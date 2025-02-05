package com.kakapo.oakane.presentation.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.model.Currency
import com.kakapo.model.goal.GoalSavingModel
import com.kakapo.model.toFormatCurrency
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.designSystem.theme.ColorScheme
import com.kakapo.oakane.presentation.ui.component.RowWrapper
import com.kakapo.oakane.presentation.ui.component.TransactionTypeIcon

@Composable
internal fun GoalSavingItemView(item: GoalSavingModel, currency: Currency, isInLog: Boolean = false) {
    val transactionType = if(isInLog) TransactionType.Expense else TransactionType.Income
    val textColor = if(isInLog) ColorScheme.error else ColorScheme.primary
    RowWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TransactionTypeIcon(type = transactionType)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.amount.toFormatCurrency(currency),
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
            Text(
                text = "Note: ${item.note}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Text(item.formattedDateCreated, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
@Preview
private fun GoalSavingItemPreview() {
    AppTheme {
        GoalSavingItemView(
            item = GoalSavingModel(
                id = 1933,
                amount = 500_000.0,
                note = "just Note",
                createdAt = 1738291013
            ),
            currency = Currency.IDR
        )
    }
}