package com.kakapo.data.repository.base

import com.kakapo.data.model.GoalSavingParam
import com.kakapo.model.goal.GoalSavingModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GoalSavingsRepository {
    @NativeCoroutines
    suspend fun saveGoal(saving: GoalSavingParam): Result<Unit>
    @NativeCoroutines
    fun loadGoalSavingBy(id: Long): Flow<Result<List<GoalSavingModel>>>
}