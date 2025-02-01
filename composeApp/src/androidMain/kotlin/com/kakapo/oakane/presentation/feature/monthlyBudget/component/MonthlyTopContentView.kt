package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetState
import java.util.Locale

@Composable
internal fun MonthlyTopContentView(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
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
        BudgetCurrencyTextFieldView(uiState, onEvent)

    }
}

@Composable
internal fun BudgetCurrencyTextFieldView(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    val config by remember(uiState.amountUpdated) {
        mutableStateOf(
            CurrencyTextFieldConfig(
                Locale(uiState.currency.languageCode, uiState.currency.countryCode),
                initialText = uiState.amount,
                currencySymbol = uiState.currency.symbol,
            )
        )
    }
    val state = rememberCurrencyTextFieldState(
        config = config
    ) {
        onEvent.invoke(MonthlyBudgetEvent.Changed(it))
    }

    OutlinedCurrencyTextFieldView(modifier = Modifier.fillMaxWidth(), state = state)
}