package com.kakapo.oakane.presentation.ui.component.dropdown.wallet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectWalletDropdownMenuView(state: SelectWalletDropdownMenuState) {

    LaunchedEffect(state.selectedOptionText) {
        state.filterWallets(state.selectedOptionText)
    }

    ExposedDropdownMenuBox(
        expanded = state.expanded,
        onExpandedChange = state::changeExpandedState
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            value = state.selectedOptionText,
            onValueChange = state::changeSelectedOptionText,
            shape = MaterialTheme.shapes.small,
            placeholder = { Text(state.selectedWallet.name) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(state.expanded) },
            singleLine = true,
            enabled = state.wallets.size > 1,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface
            )
        )

        if (state.filteredWallet.isNotEmpty() && state.wallets.size > 1) {
            DropdownMenu(
                modifier = Modifier.heightIn(max = 240.dp),
                properties = PopupProperties(focusable = false),
                expanded = state.expanded,
                onDismissRequest = { state.expanded = false },
            ) {
                state.filteredWallet.forEach { wallet ->
                    DropdownMenuItem(
                        onClick = { state.onClicked(wallet) },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = wallet.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                }
            }
        }
    }
}