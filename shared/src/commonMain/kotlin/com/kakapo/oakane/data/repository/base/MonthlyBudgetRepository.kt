package com.kakapo.oakane.data.repository.base

import com.kakapo.model.monthlyBudget.MonthlyBudgetModel
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface MonthlyBudgetRepository {
    @NativeCoroutines
    suspend fun add(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    @NativeCoroutines
    suspend fun hasCurrentMonthlyBudgetAtTheTime(): Result<Boolean>
    @NativeCoroutines
    suspend fun loadMonthlyBudget(): Result<MonthlyBudgetModel>
    @NativeCoroutines
    suspend fun update(monthlyBudget: MonthlyBudgetParam): Result<Unit>
    @NativeCoroutines
    suspend fun loadLimit(
        startDateOfMonth: Long,
        endDateOfMonth: Long
    ): Result<Double>
    @NativeCoroutines
    suspend fun loadActiveMonthlyBudget(): Result<Long>
}