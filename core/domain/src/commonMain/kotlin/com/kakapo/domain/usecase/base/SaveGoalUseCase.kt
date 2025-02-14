package com.kakapo.domain.usecase.base

import com.kakapo.model.goal.GoalModel

interface SaveGoalUseCase {
    suspend fun execute(goal: GoalModel): Result<Unit>
}