package com.kakapo.data.repository.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface BudgetRepository {
    @NativeCoroutines
    suspend fun setMonthlyBudget(isRecurring: Boolean): Result<Unit>
    @NativeCoroutines
    suspend fun isMonthlyBudgetRecurring(): Result<Boolean>
    @NativeCoroutines
    suspend fun saveCategoryLimit(isRecurring: Boolean): Result<Unit>
    @NativeCoroutines
    suspend fun isCategoryLimitRecurring(): Result<Boolean>
    @NativeCoroutines
    suspend fun saveRecurringBudget(budget: String): Result<Unit>
    @NativeCoroutines
    suspend fun loadRecurringBudget(): Result<String>
    @NativeCoroutines
    suspend fun saveRecurringCategory(category: String): Result<Unit>
    @NativeCoroutines
    suspend fun loadRecurringCategory(): Result<String>
}