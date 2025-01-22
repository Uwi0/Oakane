package com.kakapo.data.repository.base

import com.kakapo.data.model.CategoryLimitParam
import com.kakapo.model.category.CategoryLimitModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface CategoryLimitRepository {
    @NativeCoroutines
    suspend fun checkIFExists(categoryId: Long, monthlyBudgetId: Long): Result<Boolean>
    @NativeCoroutines
    suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit>
    @NativeCoroutines
    suspend fun loadTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double>
    @NativeCoroutines
    suspend fun loadCategoryLimitBy(categoryId: Long, monthlyBudgetId: Long): Result<CategoryLimitModel>
    @NativeCoroutines
    fun loadCategoryLimitsBy(monthlyBudgetId: Long): Flow<Result<List<CategoryLimitModel>>>
    @NativeCoroutines
    suspend fun update(categoryLimit: CategoryLimitParam): Result<Unit>
    @NativeCoroutines
    suspend fun updateIncrement(spentAmount: Double, id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun update(spentAmount: Double, id: Long): Result<Unit>
}