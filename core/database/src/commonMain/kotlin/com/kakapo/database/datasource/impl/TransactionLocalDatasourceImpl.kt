package com.kakapo.database.datasource.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GetTransactionCategory
import com.kakapo.GetTransactionCategoryBy
import com.kakapo.GetTransactions
import com.kakapo.GetTransactionsByWalletId
import com.kakapo.TransactionTable
import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.database.model.TransactionCategoryEntity
import com.kakapo.database.model.TransactionEntity
import com.kakapo.database.model.toTransactionCategoryEntity
import com.kakapo.database.model.toTransactionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionLocalDatasourceImpl(
    driver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
) : TransactionLocalDatasource {

    private val transactionDb = Database.invoke(driver).transactionEntityQueries

    override suspend fun insertTransaction(entity: TransactionEntity): Result<Unit> {
        return runCatching {
            transactionDb.insert(
                walletId = entity.walletId,
                title = entity.title,
                amount = entity.amount,
                type = entity.type,
                category = entity.category.id,
                dateCreated = entity.dateCreated,
                note = entity.note,
                imageFile = entity.imageFile
            )
        }
    }

    override suspend fun getTransactions(): Result<List<TransactionEntity>> {
        return runCatching {
            transactionDb.getTransactions()
                .executeAsList()
                .map(GetTransactions::toTransactionEntity)
        }
    }

    override suspend fun getTransaction(id: Long): Result<TransactionEntity> {
        return runCatching {
            transactionDb.getTransactionBy(id).executeAsOne().toTransactionEntity()
        }
    }

    override suspend fun updateTransaction(entity: TransactionEntity): Result<Unit> {
        return runCatching {
            transactionDb.updateTransaction(
                title = entity.title,
                amount = entity.amount,
                type = entity.type,
                category = entity.category.id,
                dateCreated = entity.dateCreated,
                note = entity.note,
                imageFile = entity.imageFile,
                walletId = entity.walletId,
                id = entity.id,
            )
        }
    }

    override suspend fun deleteTransaction(id: Long): Result<Unit> {
        return runCatching {
            transactionDb.deleteTransaction(id)
        }
    }

    override suspend fun getTotalTransactionBaseOn(
        type: Long,
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double> {
        return runCatching {
            transactionDb.getTotalTransactionBaseOn(
                type,
                startDateOfMonth,
                endDateOfMonth
            ).executeAsOne()
        }
    }

    override suspend fun getTotalTransactionBy(
        walletId: Long,
        type: Long,
        startDateOfMonth: Long,
        endDateMonth: Long
    ): Result<Double> {
        return runCatching {
            transactionDb.getTotalTransactionBy(
                walletId,
                type,
                startDateOfMonth,
                endDateMonth
            ).executeAsOne()
        }
    }

    override suspend fun getTransactionCategories(
        starDateMonth: Long,
        endDateMonth: Long
    ): Result<List<TransactionCategoryEntity>> {
        return runCatching {
            transactionDb.getTransactionCategory(starDateMonth, endDateMonth)
                .executeAsList()
                .map(GetTransactionCategory::toTransactionCategoryEntity)
        }
    }

    override suspend fun getTransactionCategoriesBy(
        walletId: Long,
        starDateMonth: Long,
        endDateMonth: Long
    ): Result<List<TransactionCategoryEntity>> {
        return runCatching {
            transactionDb.getTransactionCategoryBy(walletId, starDateMonth, endDateMonth)
                .executeAsList()
                .map(GetTransactionCategoryBy::toTransactionCategoryEntity)
        }
    }

    override suspend fun getTransactionsForBackup(): Result<List<TransactionEntity>> {
        return runCatching {
            transactionDb.getTransactionsForBackup()
                .executeAsList()
                .map(TransactionTable::toTransactionEntity)
        }
    }

    override suspend fun restoreTransactions(transactions: List<TransactionEntity>): Result<Unit> {
        return runCatching {
            transactions.forEach { transaction ->
                transactionDb.insertTransactionBackup(
                    id = transaction.id,
                    walletId = transaction.walletId,
                    title = transaction.title,
                    amount = transaction.amount,
                    type = transaction.type,
                    category = transaction.categoryId,
                    dateCreated = transaction.dateCreated,
                    note = transaction.note,
                    imageFile = transaction.imageFile
                )

            }
        }
    }

    override fun getTransactionsByWalletId(walletId: Long): Flow<Result<List<TransactionEntity>>> {
        return transactionDb.getTransactionsByWalletId(walletId)
            .asFlow()
            .mapToList(dispatcher)
            .map { transaction ->
                runCatching {
                    transaction.map(GetTransactionsByWalletId::toTransactionEntity)
                }
            }
    }

}