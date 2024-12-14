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
){
    val defaultColor: Int get() = colors.first().toColorInt()
}

sealed class WalletsEffect{
    data object NavigateBack: WalletsEffect()
}

sealed class WalletsEvent{
    data object NavigateBack: WalletsEvent()
    data class OnChange(val query: String): WalletsEvent()
    data class Sheet(val shown: Boolean): WalletsEvent()
}