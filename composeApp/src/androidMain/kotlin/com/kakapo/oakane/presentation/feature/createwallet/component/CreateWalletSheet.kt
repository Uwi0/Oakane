package com.kakapo.oakane.presentation.feature.createwallet.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.sheet.SelectColorView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectIconSheetView
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletEvent
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletSheetContent
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateWalletSheet(
    sheetState: SheetState,
    state: CreateWalletState,
    onEvent: (CreateWalletEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onEvent.invoke(CreateWalletEvent.ShowSheet(state.sheetContent, false))
        },
        content = {
            CreateWalletSheetContent(state = state, onEvent = onEvent)
        }
    )
}

@Composable
private fun CreateWalletSheetContent(
    state: CreateWalletState,
    onEvent: (CreateWalletEvent) -> Unit
) {
    when (state.sheetContent) {
        CreateWalletSheetContent.Color -> SelectColorView(
            defaultColor = state.defaultColor,
            onSelectedColor = { colorHex -> onEvent.invoke(CreateWalletEvent.SelectedColor(colorHex)) },
        )

        CreateWalletSheetContent.Icon -> SelectIconSheetView(
            defaultColor = state.defaultColor,
            selectionIcon = state.selectedIconName,
            onSelectedIcon = { name -> onEvent.invoke(CreateWalletEvent.SelectedIcon(name)) },
            onPickImage = { file -> onEvent.invoke(CreateWalletEvent.SelectedImage(file)) },
            onConfirm = { onEvent.invoke(CreateWalletEvent.ShowSheet(state.sheetContent, false)) }
        )
    }
}