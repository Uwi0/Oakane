package com.kakapo.oakane.presentation.feature.wallets.component.sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import co.touchlab.kermit.Logger
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.ColorSelector
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.sheet.CreateWalletSheetContentView
import com.kakapo.oakane.presentation.ui.component.sheet.CreateWalletSheetEvent
import com.kakapo.oakane.presentation.ui.component.sheet.SelectColorView
import com.kakapo.oakane.presentation.ui.component.sheet.SelectIconSheetView
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletsSheet(
    sheetState: SheetState,
    uiState: WalletsState,
    onEvent: (WalletsEvent) -> Unit
) {
    val selectedIcon = SelectedIconModel(
        imageFile = uiState.imageFile,
        defaultIcon = uiState.selectedIcon,
        color = uiState.defaultColor
    )

    val colorSelector = ColorSelector(
        defaultColor = uiState.defaultColor,
        colorsHex = uiState.colors
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(WalletsEvent.IsSheet(shown = false)) }
    ) {
        val textFieldConfig by remember {
            mutableStateOf(
                CurrencyTextFieldConfig(
                    Locale(uiState.currency.languageCode, uiState.currency.countryCode),
                    initialText = uiState.startBalance,
                    currencySymbol = uiState.currency.symbol
                )
            )
        }

        AnimatedContent(uiState.sheetContent, label = "animate_wallets_sheet") { content ->
            when (content) {
                WalletSheetContent.Create -> CreateWalletSheetContentView(
                    walletName = uiState.walletName,
                    selectedIcon = selectedIcon,
                    colorSelector = colorSelector,
                    isEditMode = uiState.walletId != 0L,
                    textFieldConfig = textFieldConfig,
                    onEvent = { event -> onCreateWalletEvent(event, onEvent) }
                )

                WalletSheetContent.SelectIcon -> SelectIconSheetView(
                    defaultColor = uiState.defaultColor,
                    selectionIcon = uiState.selectedIcon,
                    onSelectedIcon = { iconName -> onEvent.invoke(WalletsEvent.SelectedIcon(iconName)) },
                    onPickImage = { file -> onEvent.invoke(WalletsEvent.SelectedImage(file)) },
                    onConfirm = { onEvent.invoke(WalletsEvent.ConfirmIcon) }
                )

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

private fun onCreateWalletEvent(
    createWalletEvent: CreateWalletSheetEvent,
    onEvent: (WalletsEvent) -> Unit
) {
    when (createWalletEvent) {
        is CreateWalletSheetEvent.ChangeStartBalance -> {
            Logger.d("WalletsSheet: onCreateWalletEvent: ChangeStartBalance: ${createWalletEvent.balance}")
            onEvent.invoke(WalletsEvent.ChangeStart(createWalletEvent.balance))
        }

        is CreateWalletSheetEvent.ChangeWalletName -> {
            onEvent.invoke(WalletsEvent.OnChangeWallet(createWalletEvent.name))
        }

        CreateWalletSheetEvent.ClickedColorBrush -> {
            onEvent.invoke(WalletsEvent.SelectedSheet(WalletSheetContent.SelectColor))
        }

        CreateWalletSheetEvent.ClickedIcon -> {
            onEvent.invoke(WalletsEvent.SelectedSheet(WalletSheetContent.SelectIcon))
        }

        CreateWalletSheetEvent.DeleteWallet -> {
            onEvent.invoke(WalletsEvent.Dialog(shown = true))
        }

        CreateWalletSheetEvent.SaveWallet -> {
            onEvent.invoke(WalletsEvent.SaveWallet)
        }

        is CreateWalletSheetEvent.SelectedColor -> {
            onEvent.invoke(WalletsEvent.SelectWallet(createWalletEvent.hex))
        }
    }
}