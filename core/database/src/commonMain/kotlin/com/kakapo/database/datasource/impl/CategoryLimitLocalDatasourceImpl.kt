package com.kakapo.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.CategoryLimitTable
import com.kakapo.Database
import com.kakapo.GetCategoryLimits
import com.kakapo.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.database.model.CategoryLimitEntity
import com.kakapo.database.model.toCategoryLimitEntity

class CategoryLimitLocalDatasourceImpl(sqlDriver: SqlDriver) : CategoryLimitLocalDatasource {

    private val categoryLimitQueries = Database(sqlDriver).categoryLimitEntityQueries

    override suspend fun checkIFExists(categoryId: Long, monthlyBudgetId: Long): Result<Boolean> {
        return runCatching {
            categoryLimitQueries.checkCategoryLimitExists(
                categoryId,
                monthlyBudgetId
            ).executeAsOne()
        }
    }

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

    override suspend fun getCategoryLimitBy(
        categoryId: Long,
        monthlyBudgetId: Long
    ): Result<CategoryLimitEntity> {
        return runCatching {
            categoryLimitQueries.getCategoryLimitBy(categoryId, monthlyBudgetId)
                .executeAsOne()
                .toCategoryLimitEntity()
        }
    }

    override suspend fun update(categoryLimit: CategoryLimitEntity): Result<Unit> {
        return runCatching {
            categoryLimitQueries.updateCategoryLimit(
                categoryId = categoryLimit.categoryId,
                limitAmount = categoryLimit.limitAmount,
                updatedAt = categoryLimit.updatedAt,
                id = categoryLimit.id,
            )
        }
    }

    override suspend fun updateIncrement(spentAmount: Double, id: Long): Result<Unit> {
        return runCatching {
            categoryLimitQueries.updateIncrementSpendAmount(spentAmount = spentAmount, id = id)
        }
    }

    override suspend fun update(spentAmount: Double, id: Long): Result<Unit> {
        return runCatching {
            categoryLimitQueries.updateSpentAmount(spentAmount = spentAmount, id = id)
        }
    }

    override suspend fun getCategoryLimitsForBackup(): Result<List<CategoryLimitEntity>> {
        return runCatching {
            categoryLimitQueries.getCategoryLimitForBackup()
                .executeAsList()
                .map(CategoryLimitTable::toCategoryLimitEntity)
        }
    }

    override suspend fun restoreCategoryLimits(categoryLimits: List<CategoryLimitEntity>): Result<Unit> {
        return runCatching {
            categoryLimits.forEach {
                categoryLimitQueries.insertCategoryLimitBackup(
                    id = it.id,
                    categoryId = it.categoryId,
                    monthlyBudgetId = it.monthlyBudgetId,
                    limitAmount = it.limitAmount,
                    spentAmount = it.spentAmount,
                    updatedAt = it.updatedAt,
                    createdAt = it.createdAt
                )
            }
        }
    }
}