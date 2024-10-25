package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.CategoryEntity

interface CategoryLocalDatasource {
    suspend fun getCategories(): Result<List<CategoryEntity>>
}