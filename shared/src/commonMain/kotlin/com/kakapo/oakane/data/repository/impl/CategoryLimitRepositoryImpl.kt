package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryLimitParam
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository

class CategoryLimitRepositoryImpl(
    private val localDatasource: CategoryLimitLocalDatasource
) : CategoryLimitRepository {

    override suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit> {
        return localDatasource.insert(categoryLimit.toEntity())
    }

    override suspend fun loadTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double> {
        return localDatasource.getTotalCategoryLimitBy(monthlyBudgetId)
    }
}