package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.data.model.TransactionParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface UpdateTransactionUseCase {
    @NativeCoroutines
    suspend fun executed(transaction: TransactionParam, spentAmountBefore: Double): Result<Unit>
}