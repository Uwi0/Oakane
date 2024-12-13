package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.model.toModel
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionRepositoryImpl(
    private val localDatasource: TransactionLocalDatasource
) : TransactionRepository {

    override suspend fun save(transaction: TransactionParam): Result<Unit> {
        return localDatasource.insertTransaction(transaction.toEntity())
    }

    override fun loadTransactions(): Flow<Result<List<TransactionModel>>> = flow {
        val result = localDatasource.getTransactions()
            .mapCatching { it.map(TransactionEntity::toModel) }
        emit(result)
    }

    override suspend fun deleteTransactionBy(id: Long): Result<Unit> {
        return localDatasource.deleteTransaction(id)
    }

    override suspend fun loadTransactionBy(id: Long): Result<TransactionModel> {
        return localDatasource.getTransaction(id).mapCatching { it.toModel() }
    }

    override suspend fun update(transaction: TransactionParam): Result<Unit> {
        return localDatasource.updateTransaction(transaction.toEntity())
    }

    override suspend fun loadTotalExpense(): Result<Double> {
        val typeExpense = TransactionType.Expense.ordinal.toLong()
        return localDatasource.loadTotalTransactionBaseOn(typeExpense)
    }

    override suspend fun loadTotalIncome(): Result<Double> {
        val typeIncome = TransactionType.Income.ordinal.toLong()
        return localDatasource.loadTotalTransactionBaseOn(typeIncome)
    }
}