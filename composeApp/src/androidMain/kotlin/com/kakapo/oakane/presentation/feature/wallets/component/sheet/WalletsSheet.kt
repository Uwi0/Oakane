package com.kakapo.oakane.presentation.feature.wallets.component.sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.wallets.component.sheet.content.CreateWalletContentView
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletsSheet(
    sheetState: SheetState,
    uiState: WalletsState,
    onEvent: (WalletsEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(WalletsEvent.Sheet(shown = false)) }
    ) {
        when (uiState.sheetContent) {
            WalletSheetContent.Create -> CreateWalletContentView(uiState = uiState)
            WalletSheetContent.SelectIcon -> TODO()
            WalletSheetContent.SelectColor -> TODO()
            WalletSheetContent.SelectCurrency -> TODO()
        }
    }
}