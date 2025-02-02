package com.kakapo.oakane.presentation.ui.component.sheet.wallet

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.sheet.CreateWalletSheetContentView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectColorView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectIconSheetView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletsSheetView(
    sheetState: SheetState,
    state: WalletsSheetState,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismiss) {
        AnimatedContent(state.sheetContent, label = "animate_wallets_sheet") { content ->
            WalletSheetContentView(content, state)
        }
    }
}

@Composable
private fun WalletSheetContentView(
    content: WalletSheetContent,
    state: WalletsSheetState
) {
    when (content) {
        WalletSheetContent.Create -> CreateWalletSheetContentView(state = state)
        WalletSheetContent.SelectIcon -> SelectIconSheetView(
            defaultColor = state.defaultColor,
            selectionIcon = state.selectedIconName,
            onSelectedIcon = { iconName -> state.selectedIconName = iconName },
            onPickImage = { file -> state.selectedImageFile = file },
            onConfirm = { state.sheetContent = WalletSheetContent.Create }
        )

        WalletSheetContent.SelectColor -> SelectColorView(
            defaultColor = state.defaultColor,
            onSelectedColor = { colorHex ->
                state.selectedColor = colorHex
                state.sheetContent = WalletSheetContent.Create
            }
        )
    }
}
