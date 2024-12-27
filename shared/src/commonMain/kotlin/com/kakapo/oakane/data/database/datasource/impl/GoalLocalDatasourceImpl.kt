package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GoalTable
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.database.model.GoalEntity
import com.kakapo.oakane.data.database.model.toGoalEntity
import kotlinx.serialization.json.Json

class GoalLocalDatasourceImpl(sqlDriver: SqlDriver): GoalLocalDatasource {

    private val goalTable = Database(sqlDriver).goalEntityQueries

    override suspend fun insert(goal: GoalEntity): Result<Unit> {
        return runCatching {
            goalTable.insertGoal(
                imageFile = goal.imageFile,
                name = goal.name,
                targetAmount = goal.targetAmount,
                note = goal.note,
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

    override suspend fun update(goal: GoalEntity, id: Long): Result<Unit> {
        return runCatching {
            goalTable.updateGoal(
                imageFile = goal.imageFile,
                name = goal.name,
                targetAmount = goal.targetAmount,
                savedAmount = goal.savedAmount,
                note = goal.note,
                startDate = goal.startDate,
                endDate = goal.endDate,
                id = id
            )
        }
    }

    override suspend fun getGoalsForBackup(): Result<String> {
        return runCatching {
            val goals = goalTable.getGoals().executeAsList().map(GoalTable::toGoalEntity)
            Json.encodeToString(goals)
        }
    }

}