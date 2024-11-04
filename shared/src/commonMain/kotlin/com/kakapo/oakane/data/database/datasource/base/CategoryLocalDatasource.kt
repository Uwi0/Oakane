package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.CategoryEntity

interface CategoryLocalDatasource {
    suspend fun getCategories(): Result<List<CategoryEntity>>
    suspend fun insertCategory(category: CategoryEntity): Result<Unit>
}