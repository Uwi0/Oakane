package com.kakapo.oakane.data.repository.impl

import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.database.model.TransactionCategoryEntity
import com.kakapo.database.model.TransactionEntity
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.model.toModel
import com.kakapo.oakane.data.model.toReportModel
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.report.ReportModel
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

    override suspend fun loadTotalExpense(
        walletId: Long?,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double> {
        val typeExpense = TransactionType.Expense.ordinal.toLong()
        return if (walletId != null) {
            localDatasource.getTotalTransactionBy(walletId, typeExpense, startDateOfMonth, endDateOfMonth)
        } else {
            localDatasource.getTotalTransactionBaseOn(typeExpense, startDateOfMonth, endDateOfMonth)
        }
    }

    override suspend fun loadTotalIncome(
        walletId: Long?,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double> {
        val typeIncome = TransactionType.Income.ordinal.toLong()
        return if (walletId != null) {
            localDatasource.getTotalTransactionBy(walletId, typeIncome, startDateOfMonth, endDateOfMonth)
        } else {
            localDatasource.getTotalTransactionBaseOn(typeIncome, startDateOfMonth, endDateOfMonth)
        }
    }

    override fun loadTransactionsCategories(
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Flow<Result<List<ReportModel>>> = flow {
        val result = localDatasource.getTransactionCategories(startDateOfMonth, endDateOfMonth)
            .mapCatching { it.map(TransactionCategoryEntity::toReportModel) }
        emit(result)
    }

    override fun loadTransactionsCategoriesBy(
        walletId: Long,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Flow<Result<List<ReportModel>>> = flow {
        val result = localDatasource.getTransactionCategoriesBy(
            walletId,
            startDateOfMonth,
            endDateOfMonth
        ).mapCatching { it.map(TransactionCategoryEntity::toReportModel) }

        emit(result)
    }
}