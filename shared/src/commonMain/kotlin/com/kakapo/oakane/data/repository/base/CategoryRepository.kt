package com.kakapo.oakane.data.repository.base

import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun loadCategories(): Flow<Result<List<CategoryModel>>>
    suspend fun loadCategoryBy(id: Int): Flow<Result<CategoryModel>>
    suspend fun save(category: CategoryModel): Result<Unit>
}