package com.kakapo.database.datasource.base

import com.kakapo.database.model.TransactionCategoryEntity
import com.kakapo.database.model.TransactionEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface TransactionLocalDatasource {
    @NativeCoroutines
    suspend fun insertTransaction(entity: TransactionEntity): Result<Unit>

    @NativeCoroutines
    suspend fun getTransactions(): Result<List<TransactionEntity>>

    @NativeCoroutines
    suspend fun getTransaction(id: Long): Result<TransactionEntity>

    @NativeCoroutines
    suspend fun updateTransaction(entity: TransactionEntity): Result<Unit>

    @NativeCoroutines
    suspend fun deleteTransaction(id: Long): Result<Unit>

    @NativeCoroutines
    suspend fun getTotalTransactionBaseOn(
        type: Long,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double>

    @NativeCoroutines
    suspend fun getTotalTransactionBy(
        walletId: Long,
        type: Long,
        startDateOfMonth: Long,
        endDateMonth: Long
    ): Result<Double>

    @NativeCoroutines
    suspend fun getTransactionCategories(
        starDateMonth: Long,
        endDateMonth: Long
    ): Result<List<TransactionCategoryEntity>>

    @NativeCoroutines
    suspend fun getTransactionCategoriesBy(
        walletId: Long,
        starDateMonth: Long,
        endDateMonth: Long
    ): Result<List<TransactionCategoryEntity>>

    @NativeCoroutines
    suspend fun getTransactionsForBackup(): Result<List<TransactionEntity>>

    @NativeCoroutines
    suspend fun restoreTransactions(transactions: List<TransactionEntity>): Result<Unit>

    @NativeCoroutines
    fun getTransactionsByWalletId(walletId: Long): Flow<Result<List<TransactionEntity>>>
}