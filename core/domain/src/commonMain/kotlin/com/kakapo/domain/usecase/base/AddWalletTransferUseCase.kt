package com.kakapo.domain.usecase.base

import com.kakapo.data.model.WalletTransferParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface AddWalletTransferUseCase {
    @NativeCoroutines
    suspend fun execute(walletTransfer: WalletTransferParam): Result<Unit>
}