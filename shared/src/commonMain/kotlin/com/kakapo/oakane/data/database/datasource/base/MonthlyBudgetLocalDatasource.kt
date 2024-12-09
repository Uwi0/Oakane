package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.MonthlyBudgetEntity

interface MonthlyBudgetLocalDatasource {
    suspend fun insertMonthlyBudget(entity: MonthlyBudgetEntity): Result<Unit>
    suspend fun tableIsNotEmpty(): Result<Boolean>
    suspend fun getMonthlyBudget(): Result<MonthlyBudgetEntity>
    suspend fun updateMonthlyBudget(entity: MonthlyBudgetEntity): Result<Unit>
    suspend fun getTotalBudget(): Result<Double>
    suspend fun selectActiveMonthlyBudgets(): Result<Long>
}