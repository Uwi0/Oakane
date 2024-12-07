package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.GetCategoryLimits
import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryLimitEntity
import com.kakapo.oakane.data.database.model.toCategoryLimitEntity

class CategoryLimitLocalDatasourceImpl(sqlDriver: SqlDriver) : CategoryLimitLocalDatasource {

    private val categoryLimitQueries = Database(sqlDriver).categoryLimitEntityQueries

    override suspend fun insert(categoryLimit: CategoryLimitEntity): Result<Unit> {
        return runCatching {
            categoryLimitQueries.insertCategoryLimit(
                categoryId = categoryLimit.categoryId,
                monthlyBudgetId = categoryLimit.monthlyBudgetId,
                limitAmount = categoryLimit.limitAmount,
            )
        }
    }

    override suspend fun getTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double> {
        return runCatching {
            categoryLimitQueries.getTotalCategoryLimitBy(monthlyBudgetId).executeAsOne()
        }
    }

    override suspend fun getCategoryLimitsBy(monthlyBudgetId: Long): Result<List<CategoryLimitEntity>> {
        return runCatching {
            categoryLimitQueries.getCategoryLimits(monthlyBudgetId)
                .executeAsList()
                .map(GetCategoryLimits::toCategoryLimitEntity)
        }
    }
}