package com.kakapo.oakane.presentation.viewModel.createWallet

data class CreateWalletState(
    val walletName: String = ""
) {

    companion object {
        fun default() = CreateWalletState()
    }
}

sealed class CreateWalletEffect {
    data object NavigateBack: CreateWalletEffect()
}

sealed class CreateWalletEvent {
    data object NavigateBack: CreateWalletEvent()
    data class WalletNameChanged(val name: String): CreateWalletEvent()
    data object CreateWallet: CreateWalletEvent()
}