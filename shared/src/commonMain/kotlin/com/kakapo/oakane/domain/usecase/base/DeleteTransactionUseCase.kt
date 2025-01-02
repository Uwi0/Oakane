package com.kakapo.oakane.domain.usecase.base

import com.kakapo.model.transaction.TransactionModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface DeleteTransactionUseCase {
    @NativeCoroutines
    suspend fun execute(transaction: TransactionModel): Result<Unit>
}