package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.CategoryLimitEntity

interface CategoryLimitLocalDatasource {
    suspend fun insert(categoryLimit: CategoryLimitEntity): Result<Unit>
    suspend fun getTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double>
}