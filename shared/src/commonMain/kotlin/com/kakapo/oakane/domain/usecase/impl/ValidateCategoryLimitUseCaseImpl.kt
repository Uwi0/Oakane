package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.common.startDateAndEndDateOfMonth
import com.kakapo.oakane.data.model.CategoryLimitParam
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.domain.usecase.base.ValidateCategoryLimitUseCase

class ValidateCategoryLimitUseCaseImpl(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository
) : ValidateCategoryLimitUseCase {

    override suspend fun execute(
        categoryLimit: CategoryLimitParam,
        isEditMode: Boolean
    ): Result<Unit> {
        val monthlyBudgetId = categoryLimit.monthlyBudgetId
        val newLimitAmount = categoryLimit.limitAmount
        val (startDate, endDate) = startDateAndEndDateOfMonth()
        return runCatching {
            val totalBudget = monthlyBudgetRepository.loadLimit(startDate, endDate).getOrThrow()
            val totalCategoryLimit = categoryLimitRepository.loadTotalCategoryLimitBy(monthlyBudgetId).getOrThrow()
            val alreadyExist = categoryLimitRepository.checkIFExists(categoryLimit.categoryId, monthlyBudgetId).getOrThrow()
            val newLimit = totalCategoryLimit + newLimitAmount
            when {
                newLimit >= totalBudget -> throw Exception("Limit amount must be less than total budget")
                newLimit <= 0 -> throw Exception("Limit amount must be greater than 0")
                isEditMode -> categoryLimitRepository.update(categoryLimit).getOrThrow()
                alreadyExist -> throw Exception("Category limit already exists")
                else -> categoryLimitRepository.save(categoryLimit).getOrThrow()
            }
        }
    }
}