package com.kakapo.database.datasource.base

import com.kakapo.database.model.MonthlyBudgetEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface MonthlyBudgetLocalDatasource {
    @NativeCoroutines
    suspend fun insertMonthlyBudget(entity: MonthlyBudgetEntity): Result<Unit>
    @NativeCoroutines
    suspend fun hasCurrentMonthlyBudgetIn(currentTime: Long): Result<Boolean>
    @NativeCoroutines
    suspend fun getMonthlyBudget(): Result<MonthlyBudgetEntity>
    @NativeCoroutines
    suspend fun updateMonthlyBudget(entity: MonthlyBudgetEntity): Result<Unit>
    @NativeCoroutines
    suspend fun getTotalBudgetWith(startDateOfMonth: Long, endDatOfMont: Long): Result<Double>
    @NativeCoroutines
    suspend fun selectActiveMonthlyBudgets(): Result<Long>
    @NativeCoroutines
    suspend fun getMonthlyBudgetsForBackup(): Result<List<MonthlyBudgetEntity>>
    @NativeCoroutines
    suspend fun restoreMonthlyBudgets(monthlyBudgets: List<MonthlyBudgetEntity>): Result<Unit>
}