package com.kakapo.oakane.presentation.feature.wallet.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomTextButton
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.ui.component.dropdown.wallet.SelectWalletDropdownMenuView
import com.kakapo.oakane.presentation.ui.component.dropdown.wallet.rememberSelectWalletDropdownMenuState
import com.kakapo.oakane.presentation.viewModel.wallet.WalletEvent
import com.kakapo.oakane.presentation.viewModel.wallet.WalletState
import java.util.Locale

@Composable
fun MoveBalanceDialogContentView(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        MoveBalanceDialogTopContentView(uiState = uiState, onEvent = onEvent)
        MoveBalanceDialogBottomContentView(onEvent = onEvent)
    }
}

@Composable
private fun MoveBalanceDialogTopContentView(
    uiState: WalletState,
    onEvent: (WalletEvent) -> Unit
) {
    val wallets = uiState.wallets
    val selectedWalletId = uiState.selectedWalletId
    val toWalletState = rememberSelectWalletDropdownMenuState(wallets, selectedWalletId) {
        onEvent.invoke(WalletEvent.AddSelectedWalletTo(it))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Move balance", style = MaterialTheme.typography.titleLarge)
        SelectWalletDropdownMenuView(toWalletState, label = { Text("To") })
        MoveBalanceCurrencyTextFieldView(uiState, onEvent)
        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "Note",
            value = uiState.moveBalanceNote,
            onValueChange = { onEvent.invoke(WalletEvent.AddNote(it)) }
        )
    }
}

@Composable
private fun MoveBalanceCurrencyTextFieldView(state: WalletState, onEvent: (WalletEvent) -> Unit) {
    val currencyTextFieldState = rememberCurrencyTextFieldState(
        CurrencyTextFieldConfig(
            Locale(state.wallet.currency.languageCode, state.wallet.currency.countryCode),
            currencySymbol = state.wallet.currency.symbol,
        )
    ) {
        onEvent.invoke(WalletEvent.AddBalance(it))
    }
    OutlinedCurrencyTextFieldView(
        modifier = Modifier.fillMaxWidth(),
        state = currencyTextFieldState
    )
}

@Composable
private fun MoveBalanceDialogBottomContentView(onEvent: (WalletEvent) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Spacer(Modifier.weight(1f))
        CustomTextButton(
            onClick = { onEvent.invoke(WalletEvent.ShowDialog(shown = false)) },
            contentPadding = PaddingValues(16.dp),
            content = { Text("Cancel") }
        )
        CustomButton(
            modifier = Modifier.widthIn(min = 120.dp),
            onClick = { onEvent.invoke(WalletEvent.MoveBalance)},
            contentPadding = PaddingValues(16.dp),
            content = { Text("Move") }
        )
    }
}

@Composable
@Preview
private fun MoveBalanceDialogContentPreview() {
    AppTheme {
        Surface {
            MoveBalanceDialogContentView(uiState = WalletState()) {

            }
        }
    }

}
