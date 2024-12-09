package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.data.model.TransactionParam

interface SaveTransactionUseCase {
    suspend fun execute(transaction: TransactionParam): Result<Unit>
}