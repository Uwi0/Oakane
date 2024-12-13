package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.data.model.toMonthlyBudgetModel
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetModel
import kotlinx.datetime.Clock

class MonthlyBudgetRepositoryImpl(
    private val localDatasource: MonthlyBudgetLocalDatasource
): MonthlyBudgetRepository {

    override suspend fun add(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        return localDatasource.insertMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun hasCurrentMontlyBudgetAtTheTime(): Result<Boolean> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.hasCurrentMonthlyBudgetIn(currentTime)
    }

    override suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel> {
        return localDatasource.getMonthlyBudget().mapCatching { it.toMonthlyBudgetModel() }
    }

    override suspend fun update(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        return localDatasource.updateMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun loadLimit(): Result<Double> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.getTotalBudgetWith(currentTime)
    }

    override suspend fun loadActiveMonthlyBudget(): Result<Long> {
        return localDatasource.selectActiveMonthlyBudgets()
    }

}