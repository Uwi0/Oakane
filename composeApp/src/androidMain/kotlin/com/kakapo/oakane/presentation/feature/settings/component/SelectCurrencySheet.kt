package com.kakapo.oakane.presentation.feature.settings.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.model.Currency
import com.kakapo.oakane.presentation.ui.component.sheet.SelectCurrencyView
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectCurrencySheet(
    currency: Currency,
    sheetState: SheetState,
    onEvent: (SettingsEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(SettingsEvent.OnSheet(shown = false)) }) {
        SelectCurrencyView(
            currency = currency,
            onConfirm = { onEvent.invoke(SettingsEvent.ChangeCurrency(it)) }
        )
    }
}