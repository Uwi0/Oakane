package com.kakapo.oakane.presentation.viewModel.createWallet

import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.wallet.WalletItemModel

data class CreateWalletState(
    val walletName: String = "",
    val note: String = "",
    val balance: String = "",
    val currency: Currency = Currency.IDR,
    val wallet: WalletItemModel = WalletItemModel(),
    val selectedImageFile: String = "",
    val selectedIconName: CategoryIconName = CategoryIconName.WALLET,
    val selectedColor: String = wallet.color,
    val isSheetShown: Boolean = false,
    val sheetContent: CreateWalletSheetContent = CreateWalletSheetContent.Color,
    val isEditMode: Boolean = false
) {

    val defaultColor: Long
        get() {
            val color = selectedColor.ifEmpty { "0xFF4CAF50" }
            return color.toColorLong()
        }

    fun updateShowSheet(event: CreateWalletEvent.ShowSheet) = copy(
        isSheetShown = event.shown,
        sheetContent = event.content
    )

    fun updateSelectedColor(color: CreateWalletEvent.SelectedColor) = copy(
        selectedColor = color.hex,
        sheetContent = CreateWalletSheetContent.Color,
        isSheetShown = false
    )

    fun updateSelectedIcon(icon: CreateWalletEvent.SelectedIcon) = copy(
        selectedIconName = icon.name,
        sheetContent = CreateWalletSheetContent.Icon,
        isSheetShown = false
    )

    fun updateSelectedImage(image: CreateWalletEvent.SelectedImage) = copy(
        selectedImageFile = image.file,
        sheetContent = CreateWalletSheetContent.Icon,
        isSheetShown = false
    )

    companion object {
        fun default() = CreateWalletState()
    }
}

sealed class CreateWalletEffect {
    data object NavigateBack : CreateWalletEffect()
    data class ShowError(val message: String) : CreateWalletEffect()
}

sealed class CreateWalletEvent {
    data object NavigateBack : CreateWalletEvent()
    data class WalletNameChanged(val name: String) : CreateWalletEvent()
    data class BalanceChanged(val balance: String) : CreateWalletEvent()
    data class NoteChanged(val note: String) : CreateWalletEvent()
    data object CreateWallet : CreateWalletEvent()
    data class ShowSheet(val content: CreateWalletSheetContent, val shown: Boolean) : CreateWalletEvent()
    data class SelectedIcon(val name: CategoryIconName) : CreateWalletEvent()
    data class SelectedImage(val file: String) : CreateWalletEvent()
    data class SelectedColor(val hex: String) : CreateWalletEvent()
}

enum class CreateWalletSheetContent {
    Color, Icon
}