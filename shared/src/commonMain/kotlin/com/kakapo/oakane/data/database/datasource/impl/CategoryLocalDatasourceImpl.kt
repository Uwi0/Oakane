package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.CategoryTable
import com.kakapo.Database
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.data.database.model.toCategoryEntity

class CategoryLocalDatasourceImpl(driver: SqlDriver) : CategoryLocalDatasource {

    private val categoryDb = Database.invoke(driver).categoryEntityQueries

    override suspend fun getCategories(): Result<List<CategoryEntity>> {
        return runCatching {
            categoryDb.getCategories().executeAsList().map(CategoryTable::toCategoryEntity)
        }
    }

    override suspend fun getExpenseCategories(): Result<List<CategoryEntity>> {
        return runCatching {
            categoryDb.getExpenseCategories().executeAsList().map(CategoryTable::toCategoryEntity)
        }
    }

    override suspend fun getCategoryBy(id: Int): Result<CategoryEntity> {
        return runCatching {
            categoryDb.getCategory(id = id.toLong()).executeAsOne().toCategoryEntity()
        }
    }

    override suspend fun updateCategory(entity: CategoryEntity): Result<Unit> {
        return runCatching {
            categoryDb.updateCategory(
                id = entity.id,
                name = entity.name,
                type = entity.type,
                icon = entity.icon,
                color = entity.color,
                isDefault = entity.isDefault
            )
        }
    }

    override suspend fun insertCategory(category: CategoryEntity): Result<Unit> {
        return runCatching {
            categoryDb.insertCategory(
                name = category.name,
                type = category.type,
                icon = category.icon,
                color = category.color,
                isDefault = category.isDefault
            )
        }
    }

    override suspend fun deleteCategoryBy(id: Long): Result<Unit> {
        return runCatching { categoryDb.deleteCategory(id) }
    }

    override suspend fun getCategoryColors(): Result<List<String>> {
        return runCatching { categoryDb.getCategoryColors().executeAsList().distinct() }
    }

    override suspend fun getCategoriesForBackup(): Result<List<CategoryEntity>> {
        return runCatching {
            categoryDb.getCategoriesForBackup()
                .executeAsList()
                .map(CategoryTable::toCategoryEntity)
        }
    }

    override suspend fun restoreCategories(categories: List<CategoryEntity>): Result<Unit> {
        return runCatching {
            categories.forEach {
                categoryDb.insertCategoryBackup(
                    id = it.id,
                    name = it.name,
                    type = it.type,
                    icon = it.icon,
                    color = it.color,
                    isDefault = it.isDefault
                )
            }
        }
    }
}