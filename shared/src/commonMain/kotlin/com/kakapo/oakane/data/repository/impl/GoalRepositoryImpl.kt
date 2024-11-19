package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.common.proceedResult
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.model.toGoalEntity
import com.kakapo.oakane.data.repository.base.GoalRepository
import com.kakapo.oakane.model.GoalModel

class GoalRepositoryImpl (
    private val localDatasource: GoalLocalDatasource
) : GoalRepository {

    override suspend fun save(goal: GoalModel): Result<Unit> {
        return proceedResult(
            executed = { localDatasource.insert(goal.toGoalEntity()) },
            transform = { }
        )
    }
}