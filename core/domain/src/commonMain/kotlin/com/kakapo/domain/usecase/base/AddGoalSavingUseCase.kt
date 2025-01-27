package com.kakapo.domain.usecase.base

import com.kakapo.data.model.GoalSavingParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface AddGoalSavingUseCase {
    @NativeCoroutines
    suspend fun execute(param: GoalSavingParam): Result<Unit>
}