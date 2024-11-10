package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.TransactionTable
import com.kakapo.oakane.common.proceed
import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.data.database.model.toEntity

class TransactionLocalDatasourceImpl(
    driver: SqlDriver
) : TransactionLocalDatasource {

    private val transactionDb = Database.invoke(driver).transactionEntityQueries

    override suspend fun insertTransaction(entity: TransactionEntity): Result<Unit> {
        return proceed {
            transactionDb.insert(
                id = null,
                title = entity.title,
                amount = entity.amount,
                type = entity.type,
                category = entity.category,
                dateCreated = entity.dateCreated,
                note = entity.note
            )
        }
    }

    override suspend fun getRecentTransactions(): Result<List<TransactionEntity>> {
        return proceed {
            transactionDb.getLastedt3Items().executeAsList().map(TransactionTable::toEntity)
        }
    }

    override suspend fun getTransactions(): Result<List<TransactionEntity>> {
        return proceed {
            transactionDb.getTransactions().executeAsList().map(TransactionTable::toEntity)
        }
    }

    override suspend fun getTransaction(id: Long): Result<TransactionEntity> {
        return proceed {
            transactionDb.getTransaction(id).executeAsList().first().toEntity()
        }
    }

    override suspend fun updateTransaction(entity: TransactionEntity): Result<Unit> {
        return proceed {
            transactionDb.updateTransaction(
                title = entity.title,
                amount = entity.amount,
                type = entity.type,
                category = entity.category,
                dateCreated = entity.dateCreated,
                note = entity.note,
                id = entity.id ?: 0
            )
        }
    }

    override suspend fun deleteTransaction(id: Long): Result<Unit> {
        return proceed {
            transactionDb.deleteTransaction(id)
        }
    }

}