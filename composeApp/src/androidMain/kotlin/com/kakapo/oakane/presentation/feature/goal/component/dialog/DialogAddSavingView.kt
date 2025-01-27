package com.kakapo.oakane.presentation.feature.goal.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.model.Currency
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.dropdown.wallet.SelectWalletDropdownMenuState
import com.kakapo.oakane.presentation.ui.component.dropdown.wallet.SelectWalletDropdownMenuView
import com.kakapo.oakane.presentation.ui.component.dropdown.wallet.rememberSelectWalletDropdownMenuState
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState
import java.util.Locale

@Composable
internal fun DialogAddSavingView(uiState: GoalState, onEvent: (GoalEvent) -> Unit) {
    val walletMenuState = rememberSelectWalletDropdownMenuState(
        wallets = uiState.wallets,
        selectedWalletId = uiState.selectedWallet.id
    ) { selectedWallet ->
        onEvent.invoke(GoalEvent.ChangeWallet(selectedWallet))
    }
    Surface(shape = MaterialTheme.shapes.medium) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            DialogContent(uiState, onEvent, walletMenuState)
            DialogButton(onEvent)
        }
    }
}

@Composable
private fun DialogContent(
    uiState: GoalState,
    onEvent: (GoalEvent) -> Unit,
    walletMenuState: SelectWalletDropdownMenuState
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "How much do you want to save?",
            style = MaterialTheme.typography.headlineSmall
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AddSavingTextField(currency = uiState.currency, onEvent = onEvent)
            SelectWalletDropdownMenuView(state = walletMenuState)
            CustomOutlinedTextField(
                value = uiState.note,
                placeHolder = "Note",
                onValueChange = { onEvent.invoke(GoalEvent.AddNote(it)) },
            )
        }
    }
}

@Composable
private fun AddSavingTextField(currency: Currency, onEvent: (GoalEvent) -> Unit) {
    val state = rememberCurrencyTextFieldState(
        config = CurrencyTextFieldConfig(
            Locale(currency.languageCode, currency.countryCode),
            currencySymbol = currency.symbol
        )
    ) { amount ->
        onEvent(GoalEvent.Change(amount))
    }
    OutlinedCurrencyTextFieldView(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Saving Amount...") }
    )
}

@Composable
private fun DialogButton(onEvent: (GoalEvent) -> Unit) {
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

@Composable
@Preview
private fun DialogContentPrev() {
    AppTheme {
        Surface {
            DialogContent(
                uiState = GoalState(),
                onEvent = {},
                walletMenuState = rememberSelectWalletDropdownMenuState(
                    wallets = emptyList(),
                    selectedWalletId = 0
                ) {}
            )
        }
    }
}

