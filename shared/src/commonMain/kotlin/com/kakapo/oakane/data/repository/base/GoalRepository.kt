package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.GoalModel
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun save(goal: GoalModel): Result<Unit>
    fun loadGoals(): Flow<Result<List<GoalModel>>>
}