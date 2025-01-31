package com.kakapo.database.datasource.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.GoalSavingsLocalDatasource
import com.kakapo.database.model.GoalSavingsEntity
import com.kakapo.database.model.toGoalSavingEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GoalSavingsLocalDatasourceImpl(
    sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
) : GoalSavingsLocalDatasource {

    private var goalSavingsTable = Database(sqlDriver).goalSavingEntityQueries

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

    override fun getGoalSavingsByGoal(id: Long): Flow<Result<List<GoalSavingsEntity>>> {
        return goalSavingsTable.getGoalSavingsById(id).asFlow().mapToList(dispatcher)
            .map { savings ->
                runCatching { savings.map { it.toGoalSavingEntity() } }
            }
    }

    override fun getGoalSavingsByWallet(id: Long): Flow<Result<List<GoalSavingsEntity>>> {
        return goalSavingsTable.getGoalSavingsByWalletId(id)
            .asFlow()
            .mapToList(dispatcher)
            .map { savings ->
                runCatching {
                    savings.map { it.toGoalSavingEntity() }
                }
            }
    }
}