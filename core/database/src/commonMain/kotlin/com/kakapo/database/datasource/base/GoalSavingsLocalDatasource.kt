package com.kakapo.database.datasource.base

import com.kakapo.database.model.GoalSavingsEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GoalSavingsLocalDatasource {
    @NativeCoroutines
    suspend fun insert(goalTransaction: GoalSavingsEntity): Result<Unit>
    @NativeCoroutines
    fun getGoalSavingsBy(id: Long): Flow<Result<List<GoalSavingsEntity>>>
}