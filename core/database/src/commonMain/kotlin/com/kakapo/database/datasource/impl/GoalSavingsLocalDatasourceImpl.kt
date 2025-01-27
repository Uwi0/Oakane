package com.kakapo.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.GoalSavingsLocalDatasource
import com.kakapo.database.model.GoalSavingsEntity

class GoalSavingsLocalDatasourceImpl(sqlDriver: SqlDriver): GoalSavingsLocalDatasource {

    private var goalSavingsTable = Database(sqlDriver).goalTransactionHistoryEntityQueries

    override suspend fun insert(goalTransaction: GoalSavingsEntity): Result<Unit> {
        return runCatching {
            goalSavingsTable.insertGoalSaving(
                goalId = goalTransaction.goalId,
                dateCreated = goalTransaction.dateCreated,
                amount = goalTransaction.amount,
                walletId = goalTransaction.walletId,
                note = goalTransaction.note
            )
        }
    }
}