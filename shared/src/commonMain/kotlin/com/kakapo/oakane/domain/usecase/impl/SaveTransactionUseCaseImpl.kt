package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.domain.usecase.base.SaveTransactionUseCase

class SaveTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository
) : SaveTransactionUseCase {

    override suspend fun execute(transaction: TransactionParam): Result<Unit> = runCatching {
        transactionRepository.save(transaction).getOrThrow()
        val monthlyBudgetId = getMonthlyBudgetId() ?: return@runCatching
        val categoryId = transaction.category.id
        val categoryLimitId = getCategoryLimit(categoryId, monthlyBudgetId)
        categoryLimitId?.let {
            val spentAmount = transaction.amount
            categoryLimitRepository.update(spentAmount, categoryLimitId).getOrThrow()
        }
    }

    private suspend fun getMonthlyBudgetId(): Long? {
        return  monthlyBudgetRepository.loadActiveMonthlyBudget().getOrNull()
    }

    private suspend fun getCategoryLimit(categoryId: Long, monthlyBudgetId: Long): Long? {
        val result = categoryLimitRepository.loadCategoryLimitBy(categoryId, monthlyBudgetId)
            .getOrNull()
        return result?.id
    }
}