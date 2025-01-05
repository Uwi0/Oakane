package com.kakapo.domain.usecase.base

import com.kakapo.model.transaction.TransactionModel

interface DeleteTransactionUseCase {
    suspend fun execute(transaction: TransactionModel): Result<Unit>
}