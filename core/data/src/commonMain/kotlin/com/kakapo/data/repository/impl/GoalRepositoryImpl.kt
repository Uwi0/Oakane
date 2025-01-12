package com.kakapo.data.repository.impl

import com.kakapo.data.model.toGoalEntity
import com.kakapo.data.model.toGoalModel
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.database.datasource.base.GoalLocalDatasource
import com.kakapo.model.GoalModel
import com.kakapo.model.asCurrency
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GoalRepositoryImpl (
    private val localDatasource: GoalLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource
) : GoalRepository {

    override suspend fun save(goal: GoalModel): Result<Unit> {
        return localDatasource.insert(goal.toGoalEntity())
    }

    override fun loadGoals(): Flow<Result<List<GoalModel>>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val result = localDatasource.getGoals()
            .mapCatching { goalResult ->
                goalResult.map{ goal -> goal.toGoalModel(currency) }
            }
        emit(result)
    }

    override fun loadGoalBy(id: Long): Flow<Result<GoalModel>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val result = localDatasource.getGoalBy(id).mapCatching { it.toGoalModel(currency) }
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