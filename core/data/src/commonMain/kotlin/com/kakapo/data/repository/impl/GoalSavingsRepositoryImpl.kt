package com.kakapo.data.repository.impl

import com.kakapo.data.model.GoalSavingParam
import com.kakapo.data.model.toGoalSavingModel
import com.kakapo.data.repository.base.GoalSavingsRepository
import com.kakapo.database.datasource.base.GoalSavingsLocalDatasource
import com.kakapo.model.goal.GoalSavingModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GoalSavingsRepositoryImpl(
    private val goalSavingsLocalDatasource: GoalSavingsLocalDatasource,
    private var dispatcher: CoroutineDispatcher
) : GoalSavingsRepository {

    override suspend fun saveGoal(saving: GoalSavingParam): Result<Unit> {
        return goalSavingsLocalDatasource.insert(saving.toGoalTransactionEntity())
    }

    override fun loadGoalSavingBy(id: Long): Flow<Result<List<GoalSavingModel>>> {
        return goalSavingsLocalDatasource.getGoalSavingsBy(id).map { result ->
            result.map { savings -> savings.map { it.toGoalSavingModel() } }
        }.flowOn(dispatcher)
    }
}
