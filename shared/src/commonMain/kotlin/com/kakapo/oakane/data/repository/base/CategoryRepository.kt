package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.category.CategoryModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    @NativeCoroutines
    fun loadCategories(): Flow<Result<List<CategoryModel>>>
    @NativeCoroutines
    fun loadCategoryBy(id: Int): Flow<Result<CategoryModel>>
    @NativeCoroutines
    fun loadExpenseCategories(): Flow<Result<List<CategoryModel>>>
    @NativeCoroutines
    fun loadCategoryColors(): Flow<Result<List<String>>>
    @NativeCoroutines
    suspend fun save(category: CategoryModel): Result<Unit>
    @NativeCoroutines
    suspend fun update(category: CategoryModel): Result<Unit>
    @NativeCoroutines
    suspend fun deleteCategoryBy(id: Long): Result<Unit>
    @NativeCoroutines
    suspend fun loadCategoryForBackup(): Result<String>
}