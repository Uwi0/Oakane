package com.kakapo.data.repository.impl

import com.kakapo.data.model.MonthlyBudgetParam
import com.kakapo.data.model.toMonthlyBudgetModel
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.model.monthlyBudget.MonthlyBudgetModel
import kotlinx.datetime.Clock

class MonthlyBudgetRepositoryImpl(
    private val localDatasource: MonthlyBudgetLocalDatasource
): MonthlyBudgetRepository {

    override suspend fun add(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        return localDatasource.insertMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun hasCurrentMonthlyBudgetAtTheTime(): Result<Boolean> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.hasCurrentMonthlyBudgetIn(currentTime)
    }

    override suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel> {
        return localDatasource.getMonthlyBudget().mapCatching { it.toMonthlyBudgetModel() }
    }

    override suspend fun update(monthlyBudget: MonthlyBudgetParam): Result<Unit> {
        return localDatasource.updateMonthlyBudget(monthlyBudget.toEntity())
    }

    override suspend fun loadLimit(startDateOfMonth: Long, endDateOfMonth: Long): Result<Double> {
        return localDatasource.getTotalBudgetWith(startDateOfMonth, endDateOfMonth)
    }

    override suspend fun loadActiveMonthlyBudget(): Result<Long> {
        return localDatasource.selectActiveMonthlyBudgets()
    }

}