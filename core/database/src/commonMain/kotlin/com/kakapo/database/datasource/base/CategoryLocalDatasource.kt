package com.kakapo.database.datasource.base

import com.kakapo.database.model.CategoryEntity
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CategoryLocalDatasource {
    @NativeCoroutines
    suspend fun getCategories(): Result<List<CategoryEntity>>
    @NativeCoroutines
    suspend fun getExpenseCategories(): Result<List<CategoryEntity>>
    @NativeCoroutines
    suspend fun getCategoryBy(id: Int): Result<CategoryEntity>
    @NativeCoroutines
    suspend fun updateCategory(entity: CategoryEntity): Result<Unit>
    @NativeCoroutines
    suspend fun insertCategory(category: CategoryEntity): Result<Unit>
    @NativeCoroutines
    suspend fun deleteCategoryBy(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun getCategoryColors(): Result<List<String>>
    @NativeCoroutines
    suspend fun getCategoriesForBackup(): Result<List<CategoryEntity>>
    @NativeCoroutines
    suspend fun restoreCategories(categories: List<CategoryEntity>): Result<Unit>
}