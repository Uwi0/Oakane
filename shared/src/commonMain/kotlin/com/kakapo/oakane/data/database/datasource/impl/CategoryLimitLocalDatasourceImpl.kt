package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryLimitEntity

class CategoryLimitLocalDatasourceImpl(sqlDriver: SqlDriver) : CategoryLimitLocalDatasource {

    private val categoryLimitQueries = Database(sqlDriver).categoryLimitEntityQueries

    override suspend fun insert(categoryLimit: CategoryLimitEntity): Result<Unit> {
        return runCatching {
            categoryLimitQueries.insertCategoryLimit(
                categoryId = categoryLimit.categoryId,
                monthlyBudgetId = categoryLimit.monthlyBudgetId,
                limitAmount = categoryLimit.limitAmount,
                spentAmount = categoryLimit.spentAmount,
            )
        }
    }
}