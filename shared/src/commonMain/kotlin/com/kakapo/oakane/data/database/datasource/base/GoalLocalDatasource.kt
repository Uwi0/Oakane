package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.GoalEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GoalLocalDatasource {
    @NativeCoroutines
    suspend fun insert(goal: GoalEntity): Result<Unit>
    @NativeCoroutines
    suspend fun getGoals(): Result<List<GoalEntity>>
    @NativeCoroutines
    suspend fun getGoalBy(id: Long): Result<GoalEntity>
    @NativeCoroutines
    suspend fun addSaved(amount: Double, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun deleteGoalBy(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun update(goal: GoalEntity, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun getGoalsForBackup(): Result<String>
}