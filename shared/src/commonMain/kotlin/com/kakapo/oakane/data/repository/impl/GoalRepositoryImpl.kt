package com.kakapo.oakane.data.repository.impl

import com.kakapo.database.datasource.base.GoalLocalDatasource
import com.kakapo.database.model.GoalEntity
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
        return localDatasource.insert(goal.toGoalEntity())
    }

    override fun loadGoals(): Flow<Result<List<GoalModel>>> = flow {
        val result = localDatasource.getGoals().mapCatching { it.map(GoalEntity::toGoalModel) }
        emit(result)
    }

    override fun loadGoalBy(id: Long): Flow<Result<GoalModel>> = flow {
        val result = localDatasource.getGoalBy(id).mapCatching { it.toGoalModel() }
        emit(result)
    }

    override suspend fun addSaved(amount: Double, id: Long): Result<Unit> {
        return localDatasource.addSaved(amount, id)
    }

    override suspend fun deleteGoalBy(id: Long): Result<Unit> {
        return localDatasource.deleteGoalBy(id)
    }

    override suspend fun update(goal: GoalModel, id: Long): Result<Unit> {
        val goalEntity = goal.toGoalEntity()
        return localDatasource.update(goalEntity, id)
    }
}