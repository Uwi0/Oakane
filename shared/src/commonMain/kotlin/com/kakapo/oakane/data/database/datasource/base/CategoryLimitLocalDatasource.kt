package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.CategoryLimitEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CategoryLimitLocalDatasource {
    @NativeCoroutines
    suspend fun checkIFExists(categoryId: Long, monthlyBudgetId: Long): Result<Boolean>
    @NativeCoroutines
    suspend fun insert(categoryLimit: CategoryLimitEntity): Result<Unit>
    @NativeCoroutines
    suspend fun getTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double>
    @NativeCoroutines
    suspend fun getCategoryLimitsBy(monthlyBudgetId: Long): Result<List<CategoryLimitEntity>>
    @NativeCoroutines
    suspend fun getCategoryLimitBy(categoryId: Long, monthlyBudgetId: Long): Result<CategoryLimitEntity>
    @NativeCoroutines
    suspend fun update(categoryLimit: CategoryLimitEntity): Result<Unit>
    @NativeCoroutines
    suspend fun updateIncrement(spentAmount: Double, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun update(spentAmount: Double, id: Long): Result<Unit>
}