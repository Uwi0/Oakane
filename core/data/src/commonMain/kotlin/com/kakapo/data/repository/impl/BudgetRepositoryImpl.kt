package com.kakapo.data.repository.impl

import com.kakapo.data.repository.base.BudgetRepository
import com.kakapo.preference.constant.BooleanKey
import com.kakapo.preference.constant.StringKey
import com.kakapo.preference.datasource.base.PreferenceDatasource

class BudgetRepositoryImpl(
    private val preferenceDatasource: PreferenceDatasource
) : BudgetRepository {

    override suspend fun setMonthlyBudget(isRecurring: Boolean): Result<Unit> = runCatching {
        preferenceDatasource.saveBooleanValue(BooleanKey.IS_RECURRING_BUDGET, isRecurring)
    }

    override suspend fun isMonthlyBudgetRecurring(): Result<Boolean> = runCatching {
        preferenceDatasource.getBooleanValue(BooleanKey.IS_RECURRING_BUDGET)
    }

    override suspend fun saveCategoryLimit(isRecurring: Boolean): Result<Unit> = runCatching {
        preferenceDatasource.saveBooleanValue(BooleanKey.IS_RECURRING_CATEGORY_LIMIT, isRecurring)
    }

    override suspend fun isCategoryLimitRecurring(): Result<Boolean> = runCatching {
        preferenceDatasource.getBooleanValue(BooleanKey.IS_RECURRING_CATEGORY_LIMIT)
    }

    override suspend fun saveRecurringBudget(budget: String): Result<Unit> = runCatching {
        preferenceDatasource.saveStringValue(StringKey.RECURRING_BUDGET_LIMIT, budget)
    }

    override suspend fun loadRecurringBudget(): Result<String> = runCatching {
        preferenceDatasource.getStringValue(StringKey.RECURRING_BUDGET_LIMIT)
    }

    override suspend fun saveRecurringCategory(category: String): Result<Unit> = runCatching {
        preferenceDatasource.saveStringValue(StringKey.RECURRING_CATEGORY_LIMIT, category)
    }

    override suspend fun loadRecurringCategory(): Result<String> = runCatching {
        preferenceDatasource.getStringValue(StringKey.RECURRING_CATEGORY_LIMIT)
    }
}