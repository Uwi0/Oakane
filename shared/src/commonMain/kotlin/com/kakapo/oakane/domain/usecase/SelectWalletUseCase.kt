package com.kakapo.oakane.domain.usecase

import com.kakapo.oakane.model.wallet.WalletItemModel

fun List<WalletItemModel>.selectedWalletUseCase(id: Long): List<WalletItemModel> {
    return map { it.copy(isSelected = it.id == id) }
}