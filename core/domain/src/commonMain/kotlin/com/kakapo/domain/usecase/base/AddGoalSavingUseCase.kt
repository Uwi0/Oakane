package com.kakapo.domain.usecase.base

import com.kakapo.data.model.GoalTransactionParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface AddGoalSavingUseCase {
    @NativeCoroutines
    suspend fun execute(param: GoalTransactionParam): Result<Unit>
}