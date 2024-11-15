package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.common.proceedResult
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.data.model.toCategoryEntity
import com.kakapo.oakane.data.model.toCategoryModel
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val localDatasource: CategoryLocalDatasource
) : CategoryRepository {

    override fun loadCategories(): Flow<Result<List<CategoryModel>>> = flow {
        val result = proceedResult(localDatasource::getCategories) { entities ->
            entities.map(CategoryEntity::toCategoryModel)
        }
        emit(result)
    }

    override fun loadCategoryBy(id: Int): Flow<Result<CategoryModel>> = flow {
        val result = proceedResult(executed = { localDatasource.getCategoryBy(id) }) { entity ->
            entity.toCategoryModel()
        }
        emit(result)
    }

    override suspend fun save(category: CategoryModel): Result<Unit> {
        return proceedResult(
            executed = { localDatasource.insertCategory(category.toCategoryEntity()) },
            transform = {}
        )
    }

    override suspend fun update(category: CategoryModel): Result<Unit> {
        val entity = category.toCategoryEntity()
        return localDatasource.updateCategory(entity)
    }

    override suspend fun deleteCategoryBy(id: Long): Result<Unit> {
        return localDatasource.deleteCategoryBy(id)
    }

}