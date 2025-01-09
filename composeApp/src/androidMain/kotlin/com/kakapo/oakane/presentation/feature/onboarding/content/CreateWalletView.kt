package com.kakapo.oakane.presentation.feature.onboarding.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme

@Composable
internal fun CreateWalletView() {
    var walletName by remember { mutableStateOf("") }
    var startingBalance by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("0xFF4CAF50") }
    val currencyConfig = CurrencyTextFieldConfig(currencySymbol = "RP")
    val currencyState = rememberCurrencyTextFieldState(currencyConfig) {
        startingBalance = it
    }
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = walletName,
                onValueChange = { walletName = it },
                placeHolder = "Wallet Name",
            )
            CurrencyTextField(
                modifier = Modifier.fillMaxWidth(),
                state = currencyState,
                label = { Text("Starting Balance") },
            )
        }
    }
}

@Composable
@Preview
private fun CreateWalletPreview() {
    AppTheme {
        CreateWalletView()
    }
}
