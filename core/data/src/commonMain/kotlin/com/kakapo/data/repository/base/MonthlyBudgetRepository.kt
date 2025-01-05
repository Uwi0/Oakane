package com.kakapo.data.repository.base

import com.kakapo.data.model.MonthlyBudgetParam
import com.kakapo.model.monthlyBudget.MonthlyBudgetModel

interface MonthlyBudgetRepository {
    suspend fun add(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    suspend fun hasCurrentMonthlyBudgetAtTheTime(): Result<Boolean>
    suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel>
    suspend fun update(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    suspend fun loadLimit(
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double>
    suspend fun loadActiveMonthlyBudget(): Result<Long>
}