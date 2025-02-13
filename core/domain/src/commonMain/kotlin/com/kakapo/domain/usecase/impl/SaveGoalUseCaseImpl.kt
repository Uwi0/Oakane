package com.kakapo.domain.usecase.impl

import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.domain.usecase.base.SaveGoalUseCase
import com.kakapo.model.goal.GoalModel

class SaveGoalUseCaseImpl(
    private val goalRepository: GoalRepository
) : SaveGoalUseCase {

    override suspend fun execute(goal: GoalModel): Result<Unit> = runCatching {
        require(goal.amount > 0) { "Goal amount must be greater than 0" }
        require(goal.name.isNotEmpty()) { "Goal name can't be empty" }
        require(goal.startDate > 0) { "Invalid goal start date" }
        require(goal.endDate > 0) { "Invalid goal end date" }
        require(goal.startDate < goal.endDate) { "Goal end date must be greater than start date" }

        if (goal.id == 0L) {
            goalRepository.save(goal).getOrThrow()
        } else {
            goalRepository.update(goal, goal.id).getOrThrow()
        }
    }
}