package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.common.proceedResult
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.database.model.GoalEntity
import com.kakapo.oakane.data.model.toGoalEntity
import com.kakapo.oakane.data.model.toGoalModel
import com.kakapo.oakane.data.repository.base.GoalRepository
import com.kakapo.oakane.model.GoalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GoalRepositoryImpl (
    private val localDatasource: GoalLocalDatasource
) : GoalRepository {

    override suspend fun save(goal: GoalModel): Result<Unit> {
        return proceedResult(
            executed = { localDatasource.insert(goal.toGoalEntity()) },
            transform = { }
        )
    }

    override fun loadGoals(): Flow<Result<List<GoalModel>>> = flow {
        val result = proceedResult(
            executed = localDatasource::getGoals,
            transform = { result -> result.map(GoalEntity::toGoalModel) }
        )
        emit(result)
    }

    override fun loadGoalBy(id: Long): Flow<Result<GoalModel>> = flow {
        val result = proceedResult(
            executed = { localDatasource.getGoalBy(id) },
            transform = { it.toGoalModel() }
        )
        emit(result)
    }
}