package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.GoalModel
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun save(goal: GoalModel): Result<Unit>
    fun loadGoals(): Flow<Result<List<GoalModel>>>
    fun loadGoalBy(id: Long): Flow<Result<GoalModel>>
    suspend fun addSaved(amount: Double, id: Long): Result<Unit>
    suspend fun deleteGoalBy(id: Long): Result<Unit>
    suspend fun update(goal: GoalModel, id: Long): Result<Unit>
}