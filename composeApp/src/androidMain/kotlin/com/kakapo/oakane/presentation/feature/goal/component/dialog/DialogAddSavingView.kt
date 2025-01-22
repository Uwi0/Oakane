package com.kakapo.oakane.presentation.feature.goal.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.Currency
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState
import java.util.Locale

@Composable
internal fun DialogAddSavingView(uiState: GoalState, onEvent: (GoalEvent) -> Unit) {
    Surface(shape = MaterialTheme.shapes.medium) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "How much do you want to save?",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            AddSavingTextField(currency = uiState.currency, onEvent = onEvent)
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.weight(1f))
                CustomTextButton(
                    onClick = { onEvent.invoke(GoalEvent.Dialog(false)) },
                    content = { Text(text = "Cancel") }
                )
                CustomButton(
                    onClick = { onEvent.invoke(GoalEvent.AddSaving) },
                    content = { Text(text = "Save") }
                )
            }
        }
    }
}

@Composable
private fun AddSavingTextField(currency: Currency, onEvent: (GoalEvent) -> Unit) {
    val textFieldConfig = CurrencyTextFieldConfig(
        Locale(currency.languageCode, currency.countryCode),
        currencySymbol = currency.symbol
    )
    val state = rememberCurrencyTextFieldState(textFieldConfig) { amount ->
        onEvent(GoalEvent.Change(amount))
    }
    OutlinedCurrencyTextFieldView(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Saving Amount...") }
    )
}
