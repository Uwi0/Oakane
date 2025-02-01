package com.kakapo.domain.usecase.impl

import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.domain.usecase.base.SetRecurringBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SetRecurringBudgetUseCaseImpl(
    private val systemRepository: SystemRepository,
    private val dispatcher: CoroutineDispatcher
) : SetRecurringBudgetUseCase {

    override suspend fun execute(budget: String, categoryLimit: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val isRecurringBudgetDeferred = async(dispatcher) {
                systemRepository.isMonthlyBudgetRecurring()
            }
            val isRecurringCategoryDeferred = async(dispatcher) {
                systemRepository.isCategoryLimitRecurring()
            }

            val isRecurringBudget = isRecurringBudgetDeferred.await().getOrThrow()
            val isRecurringCategory = isRecurringCategoryDeferred.await().getOrThrow()

            val executeRecurringBudgetDeferred = async(dispatcher) {
                if (isRecurringBudget) {
                    systemRepository.saveRecurringBudget(budget)
                } else {
                    Result.success(Unit)
                }
            }

            val executeRecurringCategoryDeferred = async(dispatcher) {
                if (isRecurringCategory) {
                    systemRepository.saveRecurringCategory(categoryLimit)
                } else {
                    Result.success(Unit)
                }
            }

            executeRecurringBudgetDeferred.await().getOrThrow()
            executeRecurringCategoryDeferred.await().getOrThrow()
        }
    }
}