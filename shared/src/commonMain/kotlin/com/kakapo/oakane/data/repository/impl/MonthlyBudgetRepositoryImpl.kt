package com.kakapo.oakane.data.repository.impl

import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.data.model.toMonthlyBudgetModel
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.model.MonthlyBudgetModel

class MonthlyBudgetRepositoryImpl(
    private val localDatasource: MonthlyBudgetLocalDatasource
): MonthlyBudgetRepository {

    override suspend fun add(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        return localDatasource.insertMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun tableNotEmpty(): Result<Boolean> {
        return localDatasource.tableIsNotEmpty()
    }

    override suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel> {
        return localDatasource.getMonthlyBudget().mapCatching { it.toMonthlyBudgetModel() }
    }

    override suspend fun update(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        Logger.d("monthlyBudget: $monthlyBudget")
        return localDatasource.updateMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun loadLimit(): Result<Double> {
        return localDatasource.getTotalBudget()
    }

}