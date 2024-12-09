package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.oakane.model.category.CategoryLimitModel

class UpdateTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository
) : UpdateTransactionUseCase {

    override suspend fun executed(
        transaction: TransactionParam,
        spentAmountBefore: Double
    ): Result<Unit> = runCatching {
        transactionRepository.update(transaction).getOrThrow()
        val monthlyId = getMonthlyBudgetId() ?: return@runCatching
        val categoryLimit = getCategoryLimit(monthlyId, transaction.category.id) ?: return@runCatching
        val spentAmount = (categoryLimit.spent - spentAmountBefore) + transaction.amount
        categoryLimitRepository.update(spentAmount, categoryLimit.id)
    }

    private suspend fun getMonthlyBudgetId(): Long? {
        return monthlyBudgetRepository.loadActiveMonthlyBudget().getOrNull()
    }

    private suspend fun getCategoryLimit(monthlyId: Long, categoryId: Long): CategoryLimitModel? {
        return categoryLimitRepository.loadCategoryLimitBy(categoryId, monthlyId).getOrNull()
    }

}