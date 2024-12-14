package com.kakapo.oakane.presentation.viewModel.wallets

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.model.listcolor

data class WalletsState(
    val wallets: List<WalletItemModel> = emptyList(),
    val searchQuery: String = "",
    val sheetContent: WalletSheetContent = WalletSheetContent.Create,
    val isSheetShown: Boolean = false,
    val colors: List<String> = listcolor,
    val walletName: String = "",
    val selectedColor: String = ""
){
    val defaultColor: Int get(){
        val color = selectedColor.ifEmpty { colors.first() }
        return color.toColorInt()
    }

    fun selectedWallet(color: String) = copy(
        selectedColor = color,
        sheetContent = WalletSheetContent.Create
    )
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
}