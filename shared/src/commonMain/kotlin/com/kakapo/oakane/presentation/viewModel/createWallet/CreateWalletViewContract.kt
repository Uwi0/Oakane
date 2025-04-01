package com.kakapo.oakane.presentation.viewModel.createWallet

import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletItemModel

data class CreateWalletState(
    val walletName: String = "",
    val note: String = "",
    val balance: String = "",
    val currency: Currency = Currency.IDR,
    val walletItemModel: WalletItemModel = WalletItemModel()
) {

    companion object {
        fun default() = CreateWalletState()
    }
}

sealed class CreateWalletEffect {
    data object NavigateBack: CreateWalletEffect()
    data class ShowError(val message: String): CreateWalletEffect()
}

sealed class CreateWalletEvent {
    data object NavigateBack: CreateWalletEvent()
    data class WalletNameChanged(val name: String): CreateWalletEvent()
    data class BalanceChanged(val balance: String): CreateWalletEvent()
    data class NoteChanged(val note: String): CreateWalletEvent()
    data object CreateWallet: CreateWalletEvent()
}