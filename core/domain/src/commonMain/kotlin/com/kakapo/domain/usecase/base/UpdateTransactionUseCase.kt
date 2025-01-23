package com.kakapo.domain.usecase.base

import com.kakapo.data.model.TransactionParam
import com.kakapo.model.transaction.TransactionModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface UpdateTransactionUseCase {
    @NativeCoroutines
    suspend fun executed(transaction: TransactionParam, transactionBefore: TransactionModel): Result<Unit>
}