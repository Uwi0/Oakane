package com.kakapo.oakane.presentation.viewModel.wallets

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.presentation.model.WalletSheetContent
import kotlin.native.ObjCName

@ObjCName("WalletsStateKt")
data class WalletsState(
    val walletId: Long = 0,
    val wallets: List<WalletItemModel> = emptyList(),
    val searchQuery: String = "",
    val sheetContent: WalletSheetContent = WalletSheetContent.Create,
    val isSheetShown: Boolean = false,
    val colors: List<String> = emptyList(),
    val walletName: String = "",
    val selectedColor: String = "",
    val startBalance: String = "",
    val selectedIcon: CategoryIconName = CategoryIconName.WALLET,
    val imageFile: String = "",
    val currency: Currency = Currency.IDR
){

    val defaultColor: Long get(){
        val color = selectedColor.ifEmpty { colors.first() }
        return color.toColorLong()
    }

    val startBalanceValue: Int get() = startBalance.toIntOrNull() ?: 0

    fun selectedWallet(color: String) = copy(
        selectedColor = color,
        sheetContent = WalletSheetContent.Create
    )

    fun updateImage(file: String): WalletsState = copy(
        imageFile = file,
        sheetContent = WalletSheetContent.Create
    )

    fun resetWalletsSheet(): WalletsState = copy(
        walletId = 0,
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
            id = walletId,
            balance = startBalance.ifEmpty { "0" }.asRealCurrencyValue(),
            name = walletName,
            isDefaultIcon = imageFile.isEmpty(),
            icon = icon,
            color = selectedColor.ifEmpty { "0xFF4CAF50" }
        )
    }
}

sealed class WalletsEffect{
    data object NavigateBack: WalletsEffect()
    data class ShowError(val message: String): WalletsEffect()
    data object DismissBottomSheet: WalletsEffect()
    data class NavigateToWallet(val id: Long): WalletsEffect()
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
    data class SelectPrimaryWalletBy(val id: Long): WalletsEvent()
    data class ClickedWallet(val item: WalletItemModel): WalletsEvent()
}