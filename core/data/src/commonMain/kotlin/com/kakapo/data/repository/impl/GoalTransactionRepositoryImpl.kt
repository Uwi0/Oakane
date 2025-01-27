package com.kakapo.data.repository.impl

import com.kakapo.data.model.GoalTransactionParam
import com.kakapo.data.repository.base.GoalTransactionRepository
import com.kakapo.database.datasource.base.GoalTransactionLocalDatasource

class GoalTransactionRepositoryImpl(
    private val goalTransactionLocalDatasource: GoalTransactionLocalDatasource
): GoalTransactionRepository {

    override suspend fun saveGoal(transaction: GoalTransactionParam): Result<Unit> {
        return goalTransactionLocalDatasource.insert(transaction.toGoalTransactionEntity())
    }
}
