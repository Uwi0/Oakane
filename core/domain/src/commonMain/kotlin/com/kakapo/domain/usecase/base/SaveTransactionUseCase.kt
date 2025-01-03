package com.kakapo.domain.usecase.base

import com.kakapo.data.model.TransactionParam

interface SaveTransactionUseCase {
    suspend fun execute(transaction: TransactionParam): Result<Unit>
}