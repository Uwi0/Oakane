package com.kakapo.data.repository.base

import com.kakapo.data.model.WalletTransferParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface WalletTransferRepository {
    @NativeCoroutines
    suspend fun save(walletTransfer: WalletTransferParam): Result<Unit>
}