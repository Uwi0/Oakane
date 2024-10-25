package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.CategoryTable
import com.kakapo.Database
import com.kakapo.oakane.common.proceed
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.data.database.model.toEntity

class CategoryLocalDatasourceImpl(driver: SqlDriver): CategoryLocalDatasource {

    private val categoryDb = Database.invoke(driver).categoryEntityQueries

    override suspend fun getCategories(): Result<List<CategoryEntity>> {
        return proceed {
            categoryDb.getCategories().executeAsList().map(CategoryTable::toEntity)
        }
    }
}