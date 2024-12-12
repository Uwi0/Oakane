package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.model.MonthlyBudgetModel

interface MonthlyBudgetRepository {
    suspend fun add(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    suspend fun hasCurrentMontlyBudgetAtTheTime(): Result<Boolean>
    suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel>
    suspend fun update(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    suspend fun loadLimit(): Result<Double>
    suspend fun loadActiveMonthlyBudget(): Result<Long>
}