package com.kakapo.oakane.presentation.feature.wallets.component.sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.wallets.component.sheet.content.CreateWalletContentView
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.SelectColorView
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
        onDismissRequest = { onEvent.invoke(WalletsEvent.IsSheet(shown = false)) }
    ) {
        when (uiState.sheetContent) {
            WalletSheetContent.Create -> CreateWalletContentView(
                uiState = uiState,
                onEvent = onEvent
            )

            WalletSheetContent.SelectIcon -> { Text("Hello world")}
            WalletSheetContent.SelectCurrency -> { Text("Hello world")}
            WalletSheetContent.SelectColor -> SelectColorView(
                defaultColor = uiState.defaultColor,
                onSelectedColor = { colorHex -> onEvent.invoke(WalletsEvent.SelectWallet(colorHex)) }
            )

        }
    }
}