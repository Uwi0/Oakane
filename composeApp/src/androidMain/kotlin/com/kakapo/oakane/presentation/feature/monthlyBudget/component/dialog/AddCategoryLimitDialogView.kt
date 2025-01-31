package com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCategoryLimitDialogView(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    val state = rememberAddCategoryLimitState(
        uiState.expenseCategories,
        uiState.selectedCategoryLimit,
        uiState.currency
    )
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(MonthlyBudgetEvent.Dialog(shown = false)) }
    ) {
        AddCategoryLimitDialogContentView(state = state, onEvent = onEvent)
    }
}

@Composable
private fun AddCategoryLimitDialogContentView(
    state: AddCategoryLimitState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    val onClick: () -> Unit = {
        val event =
            MonthlyBudgetEvent.CreateCategoryLimitBy(state.categoryId, state.formattedAmount)
        onEvent.invoke(event)
    }
    Surface(shape = RoundedCornerShape(28.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text("Add your Category Limit")
            Spacer(modifier = Modifier.size(16.dp))
            CategoryLimitCurrencyTextFieldView(state = state)
            Spacer(modifier = Modifier.size(8.dp))
            CategoryLimitDropdownMenuView(state = state)
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { onEvent.invoke(MonthlyBudgetEvent.Dialog(shown = false)) }
                ) {
                    Text("Cancel")
                }
                Spacer(Modifier.size(16.dp))
                CustomButton(
                    onClick = onClick
                ) {
                    Text("Save Limit")
                }
            }
        }
    }
}

@Composable
private fun CategoryLimitCurrencyTextFieldView(
    state: AddCategoryLimitState,
) {
    val config by remember{ mutableStateOf(
        CurrencyTextFieldConfig(
            Locale(state.currency.languageCode, state.currency.countryCode),
            initialText = state.limitAmount,
            currencySymbol = state.currency.symbol
        )
    )}

    val currencyTextFieldState = rememberCurrencyTextFieldState(
        config = config,
    ) {
        state.changedLimitAmount(it)
    }

    OutlinedCurrencyTextFieldView(
        modifier = Modifier.fillMaxWidth(),
        state = currencyTextFieldState
    )
}