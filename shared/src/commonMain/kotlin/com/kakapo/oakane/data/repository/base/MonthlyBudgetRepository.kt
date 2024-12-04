package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.model.MonthlyBudgetModel

interface MonthlyBudgetRepository {
    suspend fun save(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    suspend fun tableNotEmpty(): Result<Boolean>
    suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel>
}