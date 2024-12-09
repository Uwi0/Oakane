package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.data.model.TransactionParam

interface UpdateTransactionUseCase {
    suspend fun executed(transaction: TransactionParam, spentAmountBefore: Double): Result<Unit>
}