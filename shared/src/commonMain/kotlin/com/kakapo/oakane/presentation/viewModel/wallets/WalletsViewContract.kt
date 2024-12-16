package com.kakapo.oakane.presentation.viewModel.wallets

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.model.wallet.WalletModel
import com.kakapo.oakane.presentation.model.WalletSheetContent

data class WalletsState(
    val wallets: List<WalletItemModel> = emptyList(),
    val searchQuery: String = "",
    val sheetContent: WalletSheetContent = WalletSheetContent.Create,
    val isSheetShown: Boolean = false,
    val colors: List<String> = emptyList(),
    val walletName: String = "",
    val selectedColor: String = "",
    val startBalance: String = "",
    val selectedIcon: CategoryIconName = CategoryIconName.WALLET,
    val imageFile: String = ""
){

    val defaultColor: Int get(){
        val color = selectedColor.ifEmpty { colors.first() }
        return color.toColorInt()
    }

    fun selectedWallet(color: String) = copy(
        selectedColor = color,
        sheetContent = WalletSheetContent.Create
    )

    fun updateImage(file: String): WalletsState = copy(
        imageFile = file,
        sheetContent = WalletSheetContent.Create
    )

    fun resetWalletsSheet(): WalletsState = copy(
        walletName = "",
        selectedColor = "",
        startBalance = "",
        selectedIcon = CategoryIconName.WALLET,
        imageFile = "",
        isSheetShown = false,
    )

    fun toWalletModel(): WalletModel {
        val icon = imageFile.ifEmpty { selectedIcon.displayName }
        return WalletModel(
            id = 0,
            currency = "IDR",
            balance = startBalance.toDouble(),
            name = walletName,
            isDefaultIcon = imageFile.isNotEmpty(),
            icon = icon,
            color = selectedColor
        )
    }
}

sealed class WalletsEffect{
    data object NavigateBack: WalletsEffect()
    data class ShowError(val message: String): WalletsEffect()
    data object DismissBottomSheet: WalletsEffect()
}

sealed class WalletsEvent{
    data object NavigateBack: WalletsEvent()
    data class OnSearchBy(val query: String): WalletsEvent()
    data class IsSheet(val shown: Boolean): WalletsEvent()
    data class SelectedSheet(val content: WalletSheetContent): WalletsEvent()
    data object FeatureNotAvailable: WalletsEvent()
    data class SelectWallet(val color: String): WalletsEvent()
    data class OnChangeWallet(val name: String): WalletsEvent()
    data class ChangeStart(val balance: String): WalletsEvent()
    data class SelectedIcon(val name: CategoryIconName): WalletsEvent()
    data class SelectedImage(val file: String): WalletsEvent()
    data object ConfirmIcon: WalletsEvent()
    data object SaveWallet: WalletsEvent()
    data class SelectWalletBy(val id: Long): WalletsEvent()
}