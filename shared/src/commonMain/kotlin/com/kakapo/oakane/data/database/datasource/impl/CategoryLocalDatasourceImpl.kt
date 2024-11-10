package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.CategoryTable
import com.kakapo.Database
import com.kakapo.oakane.common.proceed
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.data.database.model.toEntity

class CategoryLocalDatasourceImpl(driver: SqlDriver) : CategoryLocalDatasource {

    private val categoryDb = Database.invoke(driver).categoryEntityQueries

    override suspend fun getCategories(): Result<List<CategoryEntity>> {
        return proceed {
            categoryDb.getCategories().executeAsList().map(CategoryTable::toEntity)
        }
    }

    override suspend fun getCategoryBy(id: Int): Result<CategoryEntity> {
        return proceed {
            categoryDb.getCategory(id = id.toLong()).executeAsOne().toEntity()
        }
    }

    override suspend fun updateCategory(entity: CategoryEntity): Result<Unit> {
        return proceed {
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
        return proceed {
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
        return proceed { categoryDb.deleteCategory(id) }
    }
}