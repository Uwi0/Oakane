package com.kakapo.domain.usecase.base

import com.kakapo.data.model.WalletTransferParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface MoveWalletBalanceUseCase {
    @NativeCoroutines
    suspend fun execute(moveBalance: WalletTransferParam): Result<Unit>
}