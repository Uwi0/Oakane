package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.MonthlyBudgetEntity
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
    suspend fun getTotalBudgetWith(currentTime: Long): Result<Double>
    @NativeCoroutines
    suspend fun selectActiveMonthlyBudgets(): Result<Long>
}