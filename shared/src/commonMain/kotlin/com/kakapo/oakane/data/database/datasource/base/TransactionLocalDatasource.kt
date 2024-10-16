package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.TransactionEntity

interface TransactionLocalDatasource {
    suspend fun insertTransaction(entity: TransactionEntity): Result<Unit>
    suspend fun getRecentTransactions(): Result<List<TransactionEntity>>
    suspend fun getTransactions(): Result<List<TransactionEntity>>
}