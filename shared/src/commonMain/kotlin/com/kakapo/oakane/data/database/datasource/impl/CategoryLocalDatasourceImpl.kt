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

    override suspend fun getCategoryById(id: Int): Result<CategoryEntity> {
        return proceed {
            categoryDb.getCategory(id = id.toLong()).executeAsOne().toEntity()
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
}