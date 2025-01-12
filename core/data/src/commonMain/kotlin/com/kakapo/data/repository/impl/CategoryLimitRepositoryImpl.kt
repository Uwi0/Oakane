package com.kakapo.data.repository.impl

import com.kakapo.data.model.CategoryLimitParam
import com.kakapo.data.model.toCategoryLimitModel
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.model.asCurrency
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

class CategoryLimitRepositoryImpl(
    private val localDatasource: CategoryLimitLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource
) : CategoryLimitRepository {

    override suspend fun checkIFExists(categoryId: Long, monthlyBudgetId: Long): Result<Boolean> {
        return localDatasource.checkIFExists(categoryId, monthlyBudgetId)
    }

    override suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit> {
        return localDatasource.insert(categoryLimit.toEntity())
    }

    override suspend fun loadTotalCategoryLimitBy(monthlyBudgetId: Long): Result<Double> {
        return localDatasource.getTotalCategoryLimitBy(monthlyBudgetId)
    }

    override suspend fun loadCategoryLimitBy(
        categoryId: Long,
        monthlyBudgetId: Long
    ): Result<CategoryLimitModel> {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        return localDatasource.getCategoryLimitBy(categoryId, monthlyBudgetId)
            .mapCatching { entityResult ->
                entityResult.toCategoryLimitModel(currency)
            }
    }

    override fun loadCategoryLimitsBy(monthlyBudgetId: Long): Flow<Result<List<CategoryLimitModel>>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val result =  localDatasource.getCategoryLimitsBy(monthlyBudgetId)
            .mapCatching { entityResult ->
                entityResult.map{ entity -> entity.toCategoryLimitModel(currency) }
            }
        emit(result)
    }

    override suspend fun update(categoryLimit: CategoryLimitParam): Result<Unit> {
        val currentTimestamp = Clock.System.now().toEpochMilliseconds()
        val categoryLimitEntity = categoryLimit.toEntity().copy(updatedAt = currentTimestamp)
        return localDatasource.update(categoryLimitEntity)
    }

    override suspend fun updateIncrement(spentAmount: Double, id: Long): Result<Unit> {
        return localDatasource.updateIncrement(spentAmount, id)
    }

    override suspend fun update(spentAmount: Double, id: Long): Result<Unit> {
        return localDatasource.update(spentAmount, id)
    }
}