package com.kakapo.data.repository.base

import com.kakapo.data.model.WalletTransferParam
import com.kakapo.model.wallet.WalletTransferModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface WalletTransferRepository {
    @NativeCoroutines
    suspend fun save(walletTransfer: WalletTransferParam): Result<Unit>
    @NativeCoroutines
    fun loadLogTransferBy(walletId: Long): Flow<Result<List<WalletTransferModel>>>
}