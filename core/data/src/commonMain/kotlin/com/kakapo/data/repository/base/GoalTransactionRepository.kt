package com.kakapo.data.repository.base

import com.kakapo.data.model.GoalTransactionParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GoalTransactionRepository {
    @NativeCoroutines
    suspend fun saveGoal(transaction: GoalTransactionParam): Result<Unit>
}