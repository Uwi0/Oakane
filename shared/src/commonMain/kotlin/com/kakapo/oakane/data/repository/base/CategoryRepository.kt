package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun loadCategories(): Flow<Result<List<CategoryModel>>>
    fun loadCategoryBy(id: Int): Flow<Result<CategoryModel>>
    suspend fun save(category: CategoryModel): Result<Unit>
    suspend fun update(category: CategoryModel): Result<Unit>
    suspend fun deleteCategoryBy(id: Long): Result<Unit>
}