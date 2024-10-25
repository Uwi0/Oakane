package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.common.proceedResult
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.data.model.toModel
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val localDatasource: CategoryLocalDatasource
) : CategoryRepository {

    override suspend fun loadCategories(): Flow<Result<List<CategoryModel>>> = flow {
        val result = proceedResult(localDatasource::getCategories) { entities ->
            entities.map(CategoryEntity::toModel)
        }
        emit(result)
    }

}