package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MonthlyTopContentView() {
    Column(
        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "What is you’re Monthly budget ?",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "enter the amount of you’re budget, you can change the amount if necessary",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
        BudgetTextFieldView("") { }
    }
}