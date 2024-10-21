package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun save(transaction: TransactionParam): Result<Unit>
    fun loadRecentTransactions(): Flow<Result<List<TransactionModel>>>
    fun loadTransactions(): Flow<Result<List<TransactionModel>>>
    suspend fun deleteTransactionBy(id: Long): Result<Unit>
}