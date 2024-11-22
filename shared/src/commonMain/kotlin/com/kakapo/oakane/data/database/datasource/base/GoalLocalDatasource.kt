package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.GoalEntity

interface GoalLocalDatasource {
    suspend fun insert(goal: GoalEntity): Result<Unit>
    suspend fun getGoals(): Result<List<GoalEntity>>
}