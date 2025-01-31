package com.kakapo.domain.usecase.base

import com.kakapo.model.wallet.WalletLogItem
import kotlinx.coroutines.flow.Flow

interface WalletLogItemsUseCase {
    fun execute(walletId: Long): Flow<Result<List<WalletLogItem<*>>>>
}