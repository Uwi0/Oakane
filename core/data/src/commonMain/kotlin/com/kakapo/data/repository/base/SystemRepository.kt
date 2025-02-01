package com.kakapo.data.repository.base

import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SystemRepository {
    @NativeCoroutines
    suspend fun saveTheme(mode: Theme): Result<Unit>
    @NativeCoroutines
    suspend fun loadSavedTheme(): Result<Theme>
    @NativeCoroutines
    suspend fun saveCurrency(currency: Currency): Result<Unit>
    @NativeCoroutines
    suspend fun loadSavedCurrency(): Result<Currency>
    @NativeCoroutines
    suspend fun saveOnBoardingAlreadyRead(): Result<Unit>
    @NativeCoroutines
    suspend fun loadOnBoardingAlreadyRead(): Result<Boolean>
    @NativeCoroutines
    suspend fun changeBalance(visibility: Boolean): Result<Boolean>
    @NativeCoroutines
    suspend fun isBalanceVisible(): Result<Boolean>
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