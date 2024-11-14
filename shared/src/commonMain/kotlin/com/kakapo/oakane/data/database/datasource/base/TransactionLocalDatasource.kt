package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.TransactionEntity

interface TransactionLocalDatasource {
    suspend fun insertTransaction(entity: TransactionEntity): Result<Unit>
    suspend fun getTransactions(): Result<List<TransactionEntity>>
    suspend fun getTransaction(id: Long): Result<TransactionEntity>
    suspend fun updateTransaction(entity: TransactionEntity): Result<Unit>
    suspend fun deleteTransaction(id: Long): Result<Unit>
}