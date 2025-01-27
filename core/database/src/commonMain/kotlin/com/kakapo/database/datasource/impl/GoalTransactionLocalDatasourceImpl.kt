package com.kakapo.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.GoalTransactionLocalDatasource
import com.kakapo.database.model.GoalTransactionEntity

class GoalTransactionLocalDatasourceImpl(sqlDriver: SqlDriver): GoalTransactionLocalDatasource {

    private var goalTransactionTable = Database(sqlDriver).goalTransactionHistoryEntityQueries

    override suspend fun insert(goalTransaction: GoalTransactionEntity): Result<Unit> {
        return runCatching {
            goalTransactionTable.insertGoalTransaction(
                goalId = goalTransaction.goalId,
                dateCreated = goalTransaction.dateCreated,
                amount = goalTransaction.amount,
                walletId = goalTransaction.walletId,
                note = goalTransaction.note
            )
        }
    }
}