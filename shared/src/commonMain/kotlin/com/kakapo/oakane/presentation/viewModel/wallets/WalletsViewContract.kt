package com.kakapo.oakane.presentation.viewModel.wallets

import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel

data class WalletsState(
    val walletId: Long = 0,
    val wallets: List<WalletItemModel> = emptyList(),
    val searchQuery: String = "",
    val isSheetShown: Boolean = false,
    val currency: Currency = Currency.IDR,
    val theme: Theme = Theme.System,
    val showDrawer: Boolean = false
){

    val filteredWallets: List<WalletItemModel> get() {
        return if(searchQuery.isEmpty()) wallets
        else wallets.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    companion object {
        fun default() = WalletsState()
    }
}

sealed class WalletsEffect{
    data object NavigateBack: WalletsEffect()
    data class ShowError(val message: String): WalletsEffect()
    data class NavigateToWallet(val id: Long): WalletsEffect()
    data object OpenDrawer: WalletsEffect()
    data object NavigateToCreateWallet: WalletsEffect()
}

sealed class WalletsEvent{
    data object NavigateBack: WalletsEvent()
    data class OnSearchBy(val query: String): WalletsEvent()
    data class SelectPrimaryWalletBy(val id: Long): WalletsEvent()
    data class ClickedWallet(val item: WalletItemModel): WalletsEvent()
    data object OpenDrawer: WalletsEvent()
    data object NavigateToCreateWallet: WalletsEvent()
}