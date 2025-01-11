package com.kakapo.oakane.presentation.feature.onboarding.content.createWallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.common.toColorLong
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.theme.colorsSelector
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.ColorSelector
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.sheet.CreateWalletSheetEvent

@Composable
fun rememberCreateWalletState(onConfirm: (WalletModel) -> Unit): CreateWalletState = remember {
    CreateWalletState(onConfirm)
}


@Stable
class CreateWalletState(
    private val onConfirm: (WalletModel) -> Unit
) {

    var sheetContent by mutableStateOf(WalletSheetContent.Create)
    var walletName by mutableStateOf("")
    var color by mutableStateOf("0xFF4CAF50")
    var isSheetVisible by mutableStateOf(false)
    var currencyConfig = CurrencyTextFieldConfig(currencySymbol = "RP")
    var selectedIcon by mutableStateOf(SelectedIconModel("", CategoryIconName.WALLET, color.toColorLong()))
    var colorSelector by mutableStateOf(ColorSelector(
        defaultColor = color.ifEmpty { "0xFF4CAF50" }.toColorLong(),
        colorsHex = colorsSelector
    ))

    private var startingBalance by mutableStateOf("")

    fun walletSheetEvent(event: CreateWalletSheetEvent) {
        when (event) {
            is CreateWalletSheetEvent.ClickedIcon -> {
                sheetContent = WalletSheetContent.SelectIcon
            }

            is CreateWalletSheetEvent.ChangeWalletName -> {
                walletName = event.name
            }

            is CreateWalletSheetEvent.ChangeStartBalance -> {
                startingBalance = event.balance
            }

            is CreateWalletSheetEvent.ClickedColorBrush -> {
                sheetContent = WalletSheetContent.SelectColor
            }

            is CreateWalletSheetEvent.SelectedColor -> {
                color = event.hex
                selectedIcon = selectedIcon.copy(color = event.hex.toColorLong())
                colorSelector = colorSelector.copy(defaultColor = event.hex.toColorLong())
            }

            is CreateWalletSheetEvent.DeleteWallet -> {

            }

            CreateWalletSheetEvent.SaveWallet -> {
                confirmWallet()
            }
        }
    }

    fun onSelectedIcon(name: CategoryIconName) {
        selectedIcon = selectedIcon.copy(defaultIcon = name)
    }

    fun onPickImage(file: String) {
        selectedIcon = selectedIcon.copy(imageFile = file)
    }

    fun confirmIcon() {
        sheetContent = WalletSheetContent.Create
    }

    fun changeColor(hex: String) {
        color = hex
    }

    private fun confirmWallet() {
        val wallet = WalletModel(
            name = walletName,
            balance = startingBalance.toDoubleOrNull() ?: 0.0,
            color = color,
            icon = selectedIcon.imageFile.ifEmpty { selectedIcon.defaultIcon.displayName },
            isDefaultIcon = selectedIcon.imageFile.isEmpty()
        )
        isSheetVisible = false
        onConfirm(wallet)
    }
}