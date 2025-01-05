package com.kakapo.domain.usecase.base

import com.kakapo.data.model.TransactionParam

interface UpdateTransactionUseCase {
    suspend fun executed(transaction: TransactionParam, spentAmountBefore: Double): Result<Unit>
}