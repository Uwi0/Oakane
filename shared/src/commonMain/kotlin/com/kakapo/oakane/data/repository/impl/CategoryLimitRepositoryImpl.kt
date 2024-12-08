package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryLimitEntity
import com.kakapo.oakane.data.model.CategoryLimitParam
import com.kakapo.oakane.data.model.toCategoryLimitModel
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.model.category.CategoryLimitModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

class CategoryLimitRepositoryImpl(
    private val localDatasource: CategoryLimitLocalDatasource
) : CategoryLimitRepository {

    override suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit> {
        return localDatasource.insert(categoryLimit.toEntity())
    }

    override suspend fun loadTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double> {
        return localDatasource.getTotalCategoryLimitBy(monthlyBudgetId)
    }

    override fun loadCategoryLimitsBy(monthlyBudgetId: Long): Flow<Result<List<CategoryLimitModel>>> = flow {
        val result =  localDatasource.getCategoryLimitsBy(monthlyBudgetId)
            .mapCatching { it.map(CategoryLimitEntity::toCategoryLimitModel) }
        emit(result)
    }

    override suspend fun update(categoryLimit: CategoryLimitParam): Result<Unit> {
        val currentTimestamp = Clock.System.now().toEpochMilliseconds()
        val categoryLimitEntity = categoryLimit.toEntity().copy(updatedAt = currentTimestamp)
        return localDatasource.update(categoryLimitEntity)
    }
}