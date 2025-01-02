package com.kakapo.oakane.data.repository.base

import com.kakapo.model.GoalModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    @NativeCoroutines
    suspend fun save(goal: GoalModel): Result<Unit>
    @NativeCoroutines
    fun loadGoals(): Flow<Result<List<GoalModel>>>
    @NativeCoroutines
    fun loadGoalBy(id: Long): Flow<Result<GoalModel>>
    @NativeCoroutines
    suspend fun addSaved(amount: Double, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun deleteGoalBy(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun update(goal: GoalModel, id: Long): Result<Unit>
}