package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.GoalModel

interface GoalRepository {
    suspend fun save(goal: GoalModel): Result<Unit>
}