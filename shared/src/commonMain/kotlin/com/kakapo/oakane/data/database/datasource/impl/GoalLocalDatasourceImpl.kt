package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GoalTable
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.database.model.GoalEntity
import com.kakapo.oakane.data.database.model.toGoalEntity

class GoalLocalDatasourceImpl(sqlDriver: SqlDriver): GoalLocalDatasource {

    private val goalTable = Database(sqlDriver).goalEntityQueries

    override suspend fun insert(goal: GoalEntity): Result<Unit> {
        return runCatching {
            goalTable.insertGoal(
                imageFile = goal.imageFile,
                name = goal.name,
                targetAmount = goal.targetAmount,
                note = goal.note,
                savedAmount = 0.0,
                startDate = goal.startDate,
                endDate = goal.endDate
            )
        }
    }

    override suspend fun getGoals(): Result<List<GoalEntity>> {
        return runCatching {
            goalTable.getGoals().executeAsList().map(GoalTable::toGoalEntity)
        }
    }

    override suspend fun getGoalBy(id: Long): Result<GoalEntity> {
        return runCatching {
            goalTable.getGoalBy(id).executeAsOne().toGoalEntity()
        }
    }

    override suspend fun addSaved(amount: Double, id: Long): Result<Unit> {
        return runCatching {
            goalTable.addSavedAmount(amount, id)
        }
    }

    override suspend fun deleteGoalBy(id: Long): Result<Unit> {
        return runCatching {
            goalTable.deleteGoal(id)
        }
    }

}