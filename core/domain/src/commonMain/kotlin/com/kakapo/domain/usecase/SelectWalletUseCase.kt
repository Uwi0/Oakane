package com.kakapo.domain.usecase

import com.kakapo.model.wallet.WalletItemModel

fun List<WalletItemModel>.selectedWalletUseCase(id: Long): List<WalletItemModel> {
    return map { it.copy(isSelected = it.id == id) }
}