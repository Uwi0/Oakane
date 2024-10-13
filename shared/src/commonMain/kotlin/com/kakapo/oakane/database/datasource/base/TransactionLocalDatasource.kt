package com.kakapo.oakane.database.datasource.base

import com.kakapo.oakane.database.model.TransactionEntity

interface TransactionLocalDatasource {
    suspend fun insertTransaction(entity: TransactionEntity): Result<Unit>
    suspend fun getRecentTransactions(): Result<List<TransactionEntity>>
}