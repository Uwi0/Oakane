package com.kakapo.oakane.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.TransactionTable
import com.kakapo.oakane.common.proceed
import com.kakapo.oakane.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.database.model.TransactionEntity
import com.kakapo.oakane.database.model.toEntity

class TransactionLocalDatasourceImpl(
    private val driver: SqlDriver
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
        return proceed{
            transactionDb.getLastedt3Items().executeAsList().map(TransactionTable::toEntity)
        }
    }

}