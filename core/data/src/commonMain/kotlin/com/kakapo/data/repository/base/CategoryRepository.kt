package com.kakapo.data.repository.base

import com.kakapo.model.category.CategoryModel
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
}