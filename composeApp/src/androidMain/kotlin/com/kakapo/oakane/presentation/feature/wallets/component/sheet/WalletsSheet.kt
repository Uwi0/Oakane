package com.kakapo.oakane.presentation.feature.wallets.component.sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.wallets.component.sheet.content.CreateWalletContentView
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.sheet.SelectColorView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectIconView
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
        AnimatedContent(uiState.sheetContent, label = "animate_wallets_sheet") { content ->
            when (content) {
                WalletSheetContent.Create -> CreateWalletContentView(
                    uiState = uiState,
                    onEvent = onEvent
                )

                WalletSheetContent.SelectIcon -> SelectIconView(
                    defaultColor = uiState.defaultColor,
                    selectionIcon = uiState.selectedIcon,
                    onSelectedIcon = { iconName -> onEvent.invoke(WalletsEvent.SelectedIcon(iconName)) },
                    onPickImage = { file -> onEvent.invoke(WalletsEvent.SelectedImage(file)) },
                    onConfirm = { onEvent.invoke(WalletsEvent.ConfirmIcon) }
                )

                WalletSheetContent.SelectCurrency -> {
                    Text("Hello world")
                }

                WalletSheetContent.SelectColor -> SelectColorView(
                    defaultColor = uiState.defaultColor,
                    onSelectedColor = { colorHex ->
                        onEvent.invoke(WalletsEvent.SelectWallet(colorHex))
                    }
                )

            }
        }
    }
}