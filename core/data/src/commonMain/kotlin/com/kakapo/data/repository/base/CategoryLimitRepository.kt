package com.kakapo.data.repository.base

import com.kakapo.data.model.CategoryLimitParam
import com.kakapo.model.category.CategoryLimitModel
import kotlinx.coroutines.flow.Flow

interface CategoryLimitRepository {
    suspend fun checkIFExists(categoryId: Long, monthlyBudgetId: Long): Result<Boolean>
    suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit>
    suspend fun loadTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double>
    suspend fun loadCategoryLimitBy(categoryId: Long, monthlyBudgetId: Long): Result<CategoryLimitModel>
    fun loadCategoryLimitsBy(monthlyBudgetId: Long): Flow<Result<List<CategoryLimitModel>>>
    suspend fun update(categoryLimit: CategoryLimitParam): Result<Unit>
    suspend fun updateIncrement(spentAmount: Double, id: Long): Result<Unit>
    suspend fun update(spentAmount: Double, id: Long): Result<Unit>
}