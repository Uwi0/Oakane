package com.kakapo.domain.usecase.impl

import com.kakapo.data.repository.base.BudgetRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.domain.usecase.base.SetRecurringBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SetRecurringBudgetUseCaseImpl(
    private val budgetRepository: BudgetRepository,
    private val dispatcher: CoroutineDispatcher
) : SetRecurringBudgetUseCase {

    override suspend fun execute(
        budget: String,
        categoryLimit: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val isRecurringBudgetDeferred = async(dispatcher) {
                budgetRepository.isMonthlyBudgetRecurring()
            }
            val isRecurringCategoryDeferred = async(dispatcher) {
                budgetRepository.isCategoryLimitRecurring()
            }

            val isRecurringBudget = isRecurringBudgetDeferred.await().getOrThrow()
            val isRecurringCategory = isRecurringCategoryDeferred.await().getOrThrow()

            val executeRecurringBudgetDeferred = async(dispatcher) {
                if (isRecurringBudget) {
                    budgetRepository.saveRecurringBudget(budget)
                } else {
                    Result.success(Unit)
                }
            }

            val executeRecurringCategoryDeferred = async(dispatcher) {
                if (isRecurringCategory) {
                    budgetRepository.saveRecurringCategory(categoryLimit)
                } else {
                    Result.success(Unit)
                }
            }

            executeRecurringBudgetDeferred.await().getOrThrow()
            executeRecurringCategoryDeferred.await().getOrThrow()
        }
    }
}