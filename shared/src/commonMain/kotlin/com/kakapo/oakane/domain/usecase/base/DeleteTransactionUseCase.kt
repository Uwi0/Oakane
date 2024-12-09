package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.model.transaction.TransactionModel

interface DeleteTransactionUseCase {
    suspend fun execute(transaction: TransactionModel): Result<Unit>
}