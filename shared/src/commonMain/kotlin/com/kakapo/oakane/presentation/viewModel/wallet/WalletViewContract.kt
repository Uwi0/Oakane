package com.kakapo.oakane.presentation.viewModel.wallet

import com.kakapo.model.wallet.WalletItemModel

data class WalletState(
    val wallet: WalletItemModel = WalletItemModel()
)

sealed class WalletEffect {
    data object NavigateBack : WalletEffect()
    data class ShowError(val message: String) : WalletEffect()
}

sealed class WalletEvent {
    data object NavigateBack : WalletEvent()
    data object EditWallet : WalletEvent()
    data object DeleteWallet : WalletEvent()
}