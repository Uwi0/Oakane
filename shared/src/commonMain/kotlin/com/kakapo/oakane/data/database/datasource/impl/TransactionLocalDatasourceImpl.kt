package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GetTransactionCategory
import com.kakapo.GetTransactions
import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.model.TransactionCategoryEntity
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.data.database.model.toTransactionCategoryEntity
import com.kakapo.oakane.data.database.model.toTransactionEntity

class TransactionLocalDatasourceImpl(
    driver: SqlDriver
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
                note = entity.note
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
                id = entity.id
            )
        }
    }

    override suspend fun deleteTransaction(id: Long): Result<Unit> {
        return runCatching {
            transactionDb.deleteTransaction(id)
        }
    }

    override suspend fun loadTotalTransactionBaseOn(type: Long): Result<Double> {
        return runCatching {
            transactionDb.getTotalTransactionBaseOn(type).executeAsOne()
        }
    }

    override suspend fun getTransactionCategories(): Result<List<TransactionCategoryEntity>> {
        return runCatching {
            transactionDb.getTransactionCategory()
                .executeAsList()
                .map(GetTransactionCategory::toTransactionCategoryEntity)
        }
    }

}