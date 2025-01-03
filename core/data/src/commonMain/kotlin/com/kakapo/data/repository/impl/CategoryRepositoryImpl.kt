package com.kakapo.data.repository.impl

import com.kakapo.data.model.toCategoryEntity
import com.kakapo.data.model.toCategoryModel
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.database.datasource.base.CategoryLocalDatasource
import com.kakapo.database.model.CategoryEntity
import com.kakapo.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val localDatasource: CategoryLocalDatasource
) : CategoryRepository {

    override fun loadCategories(): Flow<Result<List<CategoryModel>>> = flow {
        val result = localDatasource.getCategories()
            .mapCatching { it.map(CategoryEntity::toCategoryModel) }
        emit(result)
    }

    override fun loadCategoryBy(id: Int): Flow<Result<CategoryModel>> = flow {
        val result = localDatasource.getCategoryBy(id).mapCatching { it.toCategoryModel() }
        emit(result)
    }

    override fun loadExpenseCategories(): Flow<Result<List<CategoryModel>>> = flow {
        val result = localDatasource.getExpenseCategories()
            .mapCatching { it.map(CategoryEntity::toCategoryModel) }
        emit(result)
    }

    override fun loadCategoryColors(): Flow<Result<List<String>>> = flow {
        val result = localDatasource.getCategoryColors()
        emit(result)
    }

    override suspend fun save(category: CategoryModel): Result<Unit> {
        val entity = category.toCategoryEntity()
        return localDatasource.insertCategory(entity)
    }

    override suspend fun update(category: CategoryModel): Result<Unit> {
        val entity = category.toCategoryEntity()
        return localDatasource.updateCategory(entity)
    }

    override suspend fun deleteCategoryBy(id: Long): Result<Unit> {
        return localDatasource.deleteCategoryBy(id)
    }
}