package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.database.model.GoalEntity

class GoalLocalDatasourceImpl(sqlDriver: SqlDriver): GoalLocalDatasource {

    private val goalTable = Database(sqlDriver).goalTableQueries

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

}