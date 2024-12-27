package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.TransactionCategoryEntity
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

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
}