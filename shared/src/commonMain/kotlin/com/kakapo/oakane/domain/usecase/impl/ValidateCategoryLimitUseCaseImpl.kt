package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.domain.usecase.base.ValidateCategoryLimitUseCase

class ValidateCategoryLimitUseCaseImpl(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository
) : ValidateCategoryLimitUseCase {

    override suspend fun execute(monthlyBudgetId: Long, newLimitAmount: Double): Result<Boolean> {
        return runCatching {
            val totalBudget = monthlyBudgetRepository.loadLimit().getOrThrow()
            val totalCategoryLimit = categoryLimitRepository.loadTotalCategoryLimitBy(monthlyBudgetId).getOrThrow()
            val newLimit = totalCategoryLimit + newLimitAmount
            newLimit <= totalBudget
        }
    }
}