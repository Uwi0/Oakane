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
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.model.colorsSelector
import com.kakapo.oakane.presentation.ui.component.HorizontalColorSelectorView
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.SelectedIconView
import com.kakapo.oakane.presentation.ui.component.sheet.wallet.WalletsSheetState

@Composable
internal fun CreateWalletSheetContentView(
    state: WalletsSheetState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CreateWalletContent(state = state)
        StartBalanceContent(
            textFieldConfig = state.textFieldConfig,
            onChangeBalance = { balance -> state.balance = balance }
        )
        NoteContent(state = state)
        ColorContent(
            state = state,
            onClickBrush = { state.sheetContent = WalletSheetContent.SelectColor },
            onClickColor = state::onSelectedColor
        )
        Spacer(Modifier.size(48.dp))
        ConfirmButtonView(
            isEditMode = state.isEditMode,
            saveWallet = { state.confirmSaveWallet() }
        )
    }
}

@Composable
private fun CreateWalletContent(
    state: WalletsSheetState
) {
    val selectedIcon = SelectedIconModel(
        imageFile = state.selectedImageFile,
        defaultIcon = state.selectedIconName,
        color = state.defaultColor
    )
    ColumnContent(title = "Create Wallet") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectedIconView(
                selectedIcon = selectedIcon,
                onClick = { state.sheetContent = WalletSheetContent.SelectIcon }
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = state.walletName,
                onValueChange = { state.walletName = it },
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
private fun NoteContent(state: WalletsSheetState) {
    ColumnContent("Note") {
        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.note,
            onValueChange = { state.note = it}
        )
    }
}

@Composable
private fun ColorContent(
    state: WalletsSheetState,
    onClickBrush: () -> Unit,
    onClickColor: (String) -> Unit
) {
    ColumnContent(title = "Wallet Color") {
        HorizontalColorSelectorView(
            defaultColor = state.defaultColor,
            colorsHex = colorsSelector,
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
    saveWallet: () -> Unit
) {
    val title = if (isEditMode) "Update" else "Create"
    CustomButton(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        onClick = saveWallet,
        content = { Text(title) }
    )
}
