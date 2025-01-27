package com.kakapo.data.repository.impl

import com.kakapo.data.model.GoalTransactionParam
import com.kakapo.data.repository.base.GoalSavingsRepository
import com.kakapo.database.datasource.base.GoalSavingsLocalDatasource

class GoalSavingsRepositoryImpl(
    private val goalSavingsLocalDatasource: GoalSavingsLocalDatasource
): GoalSavingsRepository {

    override suspend fun saveGoal(transaction: GoalTransactionParam): Result<Unit> {
        return goalSavingsLocalDatasource.insert(transaction.toGoalTransactionEntity())
    }
}
