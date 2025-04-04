package com.kakapo.oakane.presentation.viewModel.createWallet

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel

data class CreateWalletState(
    val id: Long = 0,
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

    val startBalance: Int get() {
        return if (balance.isEmpty()) 0
        else balance.asRealCurrencyValue().toInt()
    }

    fun update(wallet: WalletItemModel) = copy(
        id = wallet.id,
        walletName = wallet.name,
        note = wallet.note,
        balance = wallet.startBalance,
        currency = wallet.currency,
        selectedColor = wallet.color,
        selectedIconName = wallet.iconName,
        selectedImageFile = wallet.icon,
        isEditMode = true,
        wallet = wallet
    )

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
        selectedImageFile = "",
        isSheetShown = false
    )

    fun updateSelectedImage(image: CreateWalletEvent.SelectedImage) = copy(
        selectedImageFile = image.file,
        sheetContent = CreateWalletSheetContent.Icon,
        isSheetShown = false
    )

    fun asWalletModel() = WalletModel(
        id = id,
        name = walletName,
        note = note,
        balance = balance.asRealCurrencyValue(),
        currency = currency,
        color = selectedColor.ifEmpty { "0xFF4CAF50" },
        isDefaultIcon = selectedImageFile.isEmpty(),
        icon = selectedImageFile.ifEmpty { selectedIconName.displayName }
    )

    companion object {
        fun default() = CreateWalletState()
    }
}

sealed class CreateWalletEffect {
    data object NavigateBack : CreateWalletEffect()
    data class ShowError(val message: String) : CreateWalletEffect()
    data object SuccessCreateWallet : CreateWalletEffect()
}

sealed class CreateWalletEvent {
    data object NavigateBack : CreateWalletEvent()
    data class WalletNameChanged(val name: String) : CreateWalletEvent()
    data class BalanceChanged(val balance: String) : CreateWalletEvent()
    data class NoteChanged(val note: String) : CreateWalletEvent()
    data object SaveWallet : CreateWalletEvent()
    data class ShowSheet(val content: CreateWalletSheetContent, val shown: Boolean) : CreateWalletEvent()
    data class SelectedIcon(val name: CategoryIconName) : CreateWalletEvent()
    data class SelectedImage(val file: String) : CreateWalletEvent()
    data class SelectedColor(val hex: String) : CreateWalletEvent()
}

enum class CreateWalletSheetContent {
    Color, Icon
}