package com.kakapo.oakane.presentation.feature.wallets.component.sheet.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.ColorSelector
import com.kakapo.oakane.presentation.ui.component.HorizontalColorSelectorView
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.SelectedIconView
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun CreateWalletContentView(uiState: WalletsState, onEvent: (WalletsEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CreateWalletContent(uiState = uiState, onEvent = onEvent)
        StartBalanceContent(uiState = uiState, onEvent = onEvent)
        CurrencyContent(onEvent = onEvent)
        ColorContent(uiState = uiState, onEvent = onEvent)
        Spacer(Modifier.size(48.dp))
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            onClick = { onEvent.invoke(WalletsEvent.SaveWallet) },
            content = { Text("Save Wallet") }
        )
    }
}

@Composable
private fun CreateWalletContent(uiState: WalletsState, onEvent: (WalletsEvent) -> Unit) {
    val selectedIcon = SelectedIconModel(
        imageFile = uiState.imageFile,
        defaultIcon = uiState.selectedIcon,
        defaultColor = uiState.defaultColor
    )
    ColumnContent(title = "Create Wallet") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectedIconView(
                selectedIcon = selectedIcon,
                onClick = { onEvent.invoke(WalletsEvent.SelectedSheet(WalletSheetContent.SelectIcon)) }
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = uiState.walletName,
                onValueChange = { name -> onEvent.invoke(WalletsEvent.OnChangeWallet(name)) },
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = "Wallet Name") }
            )
        }
    }
}

@Composable
private fun StartBalanceContent(uiState: WalletsState, onEvent: (WalletsEvent) -> Unit) {
    ColumnContent(title = "Start Balance") {
        StartBalanceTextField(
            value = uiState.startBalance,
            onValueChange = { balance -> onEvent.invoke(WalletsEvent.ChangeStart(balance)) }
        )
    }
}

@Composable
private fun CurrencyContent(onEvent: (WalletsEvent) -> Unit) {
    ColumnContent(title = "Currency") {
        Surface(
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.outline),
            onClick = { onEvent.invoke(WalletsEvent.FeatureNotAvailable) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("IDR", style = MaterialTheme.typography.bodyLarge)
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ColorContent(uiState: WalletsState, onEvent: (WalletsEvent) -> Unit) {
    val colorSelector = ColorSelector(
        defaultColor = uiState.defaultColor,
        colorsHex = uiState.colors
    )
    ColumnContent(title = "Wallet Color") {
        HorizontalColorSelectorView(
            colorSelector = colorSelector,
            onClickBrush = { onEvent.invoke(WalletsEvent.SelectedSheet(WalletSheetContent.SelectColor)) },
            onClickColor = { colorHex -> onEvent.invoke(WalletsEvent.SelectWallet(colorHex)) }
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
internal fun StartBalanceTextField(value: String, onValueChange: (String) -> Unit) {

    var formattedValue by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        val unformattedValue = value.filter { it.isDigit() }
        formattedValue = if (unformattedValue.isNotEmpty()) {
            val number = unformattedValue.toLongOrNull() ?: 0L
            NumberFormat.getInstance(Locale("in", "ID")).format(number)
        } else {
            ""
        }
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = formattedValue,
        onValueChange = { newValue ->
            val unformattedValue = newValue.filter { it.isDigit() }
            val formattedText = if (unformattedValue.isNotEmpty()) {
                val number = unformattedValue.toLongOrNull() ?: 0L
                NumberFormat.getInstance(Locale("in", "ID")).format(number)
            } else {
                ""
            }
            formattedValue = formattedText
            onValueChange(unformattedValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text("0")
        },
        prefix = {
            Text("Rp.")
        },
        shape = MaterialTheme.shapes.medium,
    )
}
