package com.kakapo.oakane.data.repository.impl

import com.kakapo.TransactionTable
import com.kakapo.oakane.common.proceed
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.data.model.toModel
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionRepositoryImpl(
    private val localDatasource: TransactionLocalDatasource
) : TransactionRepository{

    override suspend fun save(transaction: TransactionParam): Result<Unit> {
        return proceed { localDatasource.insertTransaction(transaction.toEntity()) }
    }

    override fun loadRecentTransactions(): Flow<Result<List<TransactionModel>>> = flow {
        val result = proceed {
            localDatasource.getRecentTransactions()
                .getOrThrow()
                .map(TransactionEntity::toModel)
        }
        emit(result)
    }

    override fun loadTransactions(): Flow<Result<List<TransactionModel>>> = flow {
        val result = proceed {
            localDatasource.getTransactions()
                .getOrThrow()
                .map(TransactionEntity::toModel)
        }

        emit(result)
    }

    override suspend fun deleteTransactionBy(id: Long): Result<Unit> {
        return localDatasource.deleteTransaction(id)
    }
}