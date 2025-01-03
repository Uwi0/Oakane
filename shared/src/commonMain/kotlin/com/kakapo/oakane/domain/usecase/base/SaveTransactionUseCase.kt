package com.kakapo.oakane.domain.usecase.base

import com.kakapo.data.model.TransactionParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SaveTransactionUseCase {
    @NativeCoroutines
    suspend fun execute(transaction: TransactionParam): Result<Unit>
}