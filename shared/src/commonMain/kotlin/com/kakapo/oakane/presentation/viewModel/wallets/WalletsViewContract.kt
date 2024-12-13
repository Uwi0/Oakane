package com.kakapo.oakane.presentation.viewModel.wallets

import com.kakapo.oakane.model.wallet.WalletItemModel

data class WalletsState(
    val wallets: List<WalletItemModel> = emptyList(),
    val searchQuery: String = ""
)

sealed class WalletsEffect{
    data object NavigateBack: WalletsEffect()
}

sealed class WalletsEvent{
    data object NavigateBack: WalletsEvent()
    data class OnChange(val query: String): WalletsEvent()
}