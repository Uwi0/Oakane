package com.kakapo.database.datasource.base

import com.kakapo.database.model.GoalTransactionEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GoalTransactionLocalDatasource {
    @NativeCoroutines
    suspend fun insert(goalTransaction: GoalTransactionEntity): Result<Unit>
}