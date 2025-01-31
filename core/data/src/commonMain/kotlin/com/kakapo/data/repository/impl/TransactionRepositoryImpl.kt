package com.kakapo.data.repository.impl

import com.kakapo.data.model.TransactionParam
import com.kakapo.data.model.toModel
import com.kakapo.data.model.toReportModel
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.model.asCurrency
import com.kakapo.model.report.ReportModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val localDatasource: TransactionLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource,
    private val dispatcher: CoroutineDispatcher
) : TransactionRepository {

    override suspend fun save(transaction: TransactionParam): Result<Unit> {
        return localDatasource.insertTransaction(transaction.toEntity())
    }

    override fun loadTransactions(): Flow<Result<List<TransactionModel>>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val result = localDatasource.getTransactions()
            .mapCatching { transactionResult ->
                transactionResult.map { transaction -> transaction.toModel(currency) }
            }
        emit(result)
    }

    override suspend fun deleteTransactionBy(id: Long): Result<Unit> {
        return localDatasource.deleteTransaction(id)
    }

    override suspend fun loadTransactionBy(id: Long): Result<TransactionModel> {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        return localDatasource.getTransaction(id).mapCatching { it.toModel(currency) }
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
            localDatasource.getTotalTransactionBy(
                walletId,
                typeExpense,
                startDateOfMonth,
                endDateOfMonth
            )
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
            localDatasource.getTotalTransactionBy(
                walletId,
                typeIncome,
                startDateOfMonth,
                endDateOfMonth
            )
        } else {
            localDatasource.getTotalTransactionBaseOn(typeIncome, startDateOfMonth, endDateOfMonth)
        }
    }

    override fun loadTransactionsCategories(
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Flow<Result<List<ReportModel>>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val result = localDatasource.getTransactionCategories(startDateOfMonth, endDateOfMonth)
            .mapCatching { transactionResult ->
                transactionResult.map { transaction ->
                    transaction.toReportModel(currency)
                }
            }
        emit(result)
    }

    override fun loadTransactionsCategoriesBy(
        walletId: Long,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Flow<Result<List<ReportModel>>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val result = localDatasource.getTransactionCategoriesBy(
            walletId,
            startDateOfMonth,
            endDateOfMonth
        ).mapCatching { transactionResult ->
            transactionResult.map { transaction -> transaction.toReportModel(currency) }
        }

        emit(result)
    }

    override fun loadTransactionByWallet(id: Long): Flow<Result<List<TransactionModel>>> = flow {
        emit(preferenceDatasource.getSavedCurrency().asCurrency())
    }.flatMapLatest { currency ->
        localDatasource.getTransactionsByWalletId(id).map { result ->
            result.mapCatching { transactions -> transactions.map { it.toModel(currency) } }
        }
    }.flowOn(dispatcher)
}