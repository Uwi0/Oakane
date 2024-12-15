package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.CategoryEntity

interface CategoryLocalDatasource {
    suspend fun getCategories(): Result<List<CategoryEntity>>
    suspend fun getExpenseCategories(): Result<List<CategoryEntity>>
    suspend fun getCategoryBy(id: Int): Result<CategoryEntity>
    suspend fun updateCategory(entity: CategoryEntity): Result<Unit>
    suspend fun insertCategory(category: CategoryEntity): Result<Unit>
    suspend fun deleteCategoryBy(id: Long): Result<Unit>
    suspend fun getCategoryColors(): Result<List<String>>
}