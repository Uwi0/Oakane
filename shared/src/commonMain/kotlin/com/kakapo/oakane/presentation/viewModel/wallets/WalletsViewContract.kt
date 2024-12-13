package com.kakapo.oakane.presentation.viewModel.wallets

import com.kakapo.oakane.model.wallet.WalletItemModel

data class WalletsState(
    val wallets: List<WalletItemModel> = emptyList(),
)