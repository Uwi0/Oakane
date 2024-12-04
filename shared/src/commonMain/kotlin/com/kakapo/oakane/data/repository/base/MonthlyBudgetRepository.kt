package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.model.MonthlyBudgetParam

interface MonthlyBudgetRepository {
    suspend fun save(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    suspend fun tableNotEmpty(): Result<Boolean>
}