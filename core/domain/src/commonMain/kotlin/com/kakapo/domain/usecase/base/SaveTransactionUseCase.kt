package com.kakapo.domain.usecase.base

import com.kakapo.data.model.TransactionParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SaveTransactionUseCase {
    @NativeCoroutines
    suspend fun execute(transaction: TransactionParam): Result<Unit>
}