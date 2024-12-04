package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository

class MonthlyBudgetRepositoryImpl(
    private val localDatasource: MonthlyBudgetLocalDatasource
): MonthlyBudgetRepository {

    override suspend fun save(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        return localDatasource.insertMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun tableNotEmpty(): Result<Boolean> {
        return localDatasource.tableIsNotEmpty()
    }

}