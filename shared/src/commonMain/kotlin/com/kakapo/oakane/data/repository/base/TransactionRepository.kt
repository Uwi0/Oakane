package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.model.report.ReportModel
import com.kakapo.oakane.model.transaction.TransactionModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    @NativeCoroutines
    suspend fun save(transaction: TransactionParam): Result<Unit>

    @NativeCoroutines
    fun loadTransactions(): Flow<Result<List<TransactionModel>>>

    @NativeCoroutines
    suspend fun deleteTransactionBy(id: Long): Result<Unit>

    @NativeCoroutines
    suspend fun loadTransactionBy(id: Long): Result<TransactionModel>

    @NativeCoroutines
    suspend fun update(transaction: TransactionParam): Result<Unit>

    @NativeCoroutines
    suspend fun loadTotalExpense(
        walletId: Long?,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double>

    @NativeCoroutines
    suspend fun loadTotalIncome(
        walletId: Long?,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double>

    @NativeCoroutines
    fun loadTransactionsCategories(
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Flow<Result<List<ReportModel>>>

    @NativeCoroutines
    fun loadTransactionsCategoriesBy(
        walletId: Long,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Flow<Result<List<ReportModel>>>
}