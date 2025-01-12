package com.kakapo.oakane.presentation.ui.component.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.button.CustomOutlinedButton
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.ui.component.ColorSelector
import com.kakapo.oakane.presentation.ui.component.HorizontalColorSelectorView
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.SelectedIconView

sealed class CreateWalletSheetEvent {
    data object ClickedIcon : CreateWalletSheetEvent()
    data class ChangeWalletName(val name: String) : CreateWalletSheetEvent()
    data class ChangeStartBalance(val balance: String) : CreateWalletSheetEvent()
    data object ClickedColorBrush : CreateWalletSheetEvent()
    data class SelectedColor(val hex: String) : CreateWalletSheetEvent()
    data object DeleteWallet : CreateWalletSheetEvent()
    data object SaveWallet : CreateWalletSheetEvent()
}

@Composable
internal fun CreateWalletSheetContentView(
    walletName: String,
    selectedIcon: SelectedIconModel,
    colorSelector: ColorSelector,
    textFieldConfig: CurrencyTextFieldConfig = CurrencyTextFieldConfig(currencySymbol = "RP"),
    isEditMode: Boolean,
    onEvent: (CreateWalletSheetEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CreateWalletContent(
            selectedIcon = selectedIcon,
            walletName = walletName,
            onIconClick = { onEvent.invoke(CreateWalletSheetEvent.ClickedIcon) },
            onChangeWalletName = { name ->
                onEvent.invoke(CreateWalletSheetEvent.ChangeWalletName(name))
            }
        )
        StartBalanceContent(
            textFieldConfig = textFieldConfig,
            onChangeBalance = { balance ->
                onEvent.invoke(CreateWalletSheetEvent.ChangeStartBalance(balance))
            }
        )
        ColorContent(
            colorSelector = colorSelector,
            onClickBrush = { onEvent.invoke(CreateWalletSheetEvent.ClickedColorBrush) },
            onClickColor = { hex -> onEvent.invoke(CreateWalletSheetEvent.SelectedColor(hex)) }
        )
        Spacer(Modifier.size(48.dp))
        ConfirmButtonView(
            isEditMode = isEditMode,
            deleteWallet = { onEvent.invoke(CreateWalletSheetEvent.DeleteWallet) },
            saveWallet = { onEvent.invoke(CreateWalletSheetEvent.SaveWallet) }
        )
    }
}

@Composable
private fun CreateWalletContent(
    selectedIcon: SelectedIconModel,
    walletName: String,
    onIconClick: () -> Unit,
    onChangeWalletName: (String) -> Unit
) {
    ColumnContent(title = "Create Wallet") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectedIconView(
                selectedIcon = selectedIcon,
                onClick = onIconClick
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = walletName,
                onValueChange = onChangeWalletName,
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = "Wallet Name") },
                singleLine = true
            )
        }
    }
}

@Composable
private fun StartBalanceContent(
    textFieldConfig: CurrencyTextFieldConfig,
    onChangeBalance: (String) -> Unit
) {
    val state = rememberCurrencyTextFieldState(textFieldConfig) { balance ->
        onChangeBalance.invoke(balance)
    }
    ColumnContent(title = "Start Balance") {
        OutlinedCurrencyTextFieldView(
            state = state,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ColorContent(
    colorSelector: ColorSelector,
    onClickBrush: () -> Unit,
    onClickColor: (String) -> Unit
) {
    ColumnContent(title = "Wallet Color") {
        HorizontalColorSelectorView(
            colorSelector = colorSelector,
            onClickBrush = onClickBrush,
            onClickColor = onClickColor
        )
    }
}

@Composable
private fun ColumnContent(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        content.invoke(this)
    }
}

@Composable
private fun ConfirmButtonView(
    isEditMode: Boolean,
    deleteWallet: () -> Unit,
    saveWallet: () -> Unit
) {
    val title = if (isEditMode) "Update" else "Create"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditMode) {
            CustomOutlinedButton(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                onClick = deleteWallet,
                content = { Text("Delete") },
                color = MaterialTheme.colorScheme.error
            )
        }
        CustomButton(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            onClick = saveWallet,
            content = { Text(title) }
        )
    }
}
