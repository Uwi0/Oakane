package com.kakapo.database.datasource.base

import com.kakapo.database.model.GoalSavingsEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GoalSavingsLocalDatasource {
    @NativeCoroutines
    suspend fun insert(goalTransaction: GoalSavingsEntity): Result<Unit>
}