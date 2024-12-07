package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.data.database.model.CategoryLimitParam

interface CategoryLimitRepository {
    suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit>
    suspend fun loadTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double>
}