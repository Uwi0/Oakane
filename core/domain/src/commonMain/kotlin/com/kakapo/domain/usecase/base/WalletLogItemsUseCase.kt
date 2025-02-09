package com.kakapo.domain.usecase.base

import com.kakapo.model.wallet.WalletLogItem
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface WalletLogItemsUseCase {
    @NativeCoroutines
    fun execute(walletId: Long): Flow<Result<List<WalletLogItem<*>>>>
}