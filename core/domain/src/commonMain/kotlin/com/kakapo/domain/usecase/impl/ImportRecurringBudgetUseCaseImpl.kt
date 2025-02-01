package com.kakapo.domain.usecase.impl

import com.kakapo.common.getEndOfMonthUnixTime
import com.kakapo.data.model.MonthlyBudgetParam
import com.kakapo.data.model.MonthlyBudgetRecurring
import com.kakapo.data.model.decodeToCategoryLimitParams
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.domain.usecase.base.ImportRecurringBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class ImportRecurringBudgetUseCaseImpl(
    private val systemRepository: SystemRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val dispatcher: CoroutineDispatcher
) : ImportRecurringBudgetUseCase {

    override suspend fun execute(): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val budgetData = fetchRequiredData()

            if (shouldProcessBudget(data = budgetData)) {
                processBudgetAndCategories(data = budgetData)
            }
        }
    }

    private suspend fun fetchRequiredData(): BudgetData = coroutineScope {
        val hasMonthlyBudget = async {
            monthlyBudgetRepository.hasCurrentMonthlyBudgetAtTheTime().getOrDefault(false)
        }
        val hasRecurringBudget = async {
            systemRepository.isMonthlyBudgetRecurring().getOrDefault(false)
        }
        val hasRecurringCategory = async {
            systemRepository.isCategoryLimitRecurring().getOrDefault(false)
        }
        val monthlyBudgetBackup = async {
            systemRepository.loadRecurringBudget().getOrDefault("")
        }
        val categoryLimitBackup = async {
            systemRepository.loadRecurringCategory().getOrDefault("")
        }

        BudgetData(
            hasMonthlyBudget = hasMonthlyBudget.await(),
            hasRecurringBudget = hasRecurringBudget.await(),
            hasRecurringCategory = hasRecurringCategory.await(),
            monthlyBudgetBackup = monthlyBudgetBackup.await(),
            categoryLimitBackup = categoryLimitBackup.await()
        )
    }

    private fun shouldProcessBudget(data: BudgetData): Boolean = !data.hasMonthlyBudget &&
            data.hasRecurringBudget &&
            data.monthlyBudgetBackup.isNotEmpty()

    private suspend fun processBudgetAndCategories(data: BudgetData) {
        val monthlyBudget = createMonthlyBudget(data.monthlyBudgetBackup)
        monthlyBudgetRepository.add(monthlyBudget).getOrThrow()

        if (data.hasRecurringCategory && data.categoryLimitBackup.isNotEmpty()) {
            processCategories(data.categoryLimitBackup)
        }
    }

    private suspend fun processCategories(categoryLimitBackup: String) {
        val monthlyBudgetId = monthlyBudgetRepository.loadActiveMonthlyBudget().getOrThrow()
        categoryLimitBackup.decodeToCategoryLimitParams().forEach { categoryLimit ->
            categoryLimitRepository.save(categoryLimit.copy(monthlyBudgetId = monthlyBudgetId))
        }
    }

    private fun createMonthlyBudget(budget: String): MonthlyBudgetParam {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val savedBudget = MonthlyBudgetRecurring.fromEncodeString(budget)

        return MonthlyBudgetParam(
            totalBudget = savedBudget.amount,
            spentAmount = 0.0,
            startDate = currentTime,
            endDate = getEndOfMonthUnixTime(),
            createdAt = currentTime,
            updatedAt = currentTime
        )
    }

    private data class BudgetData(
        val hasMonthlyBudget: Boolean,
        val hasRecurringBudget: Boolean,
        val hasRecurringCategory: Boolean,
        val monthlyBudgetBackup: String,
        val categoryLimitBackup: String
    )
}