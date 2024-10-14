package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.model.TransactionParam

interface TransactionRepository {
    suspend fun save(transaction: TransactionParam): Result<Unit>
}