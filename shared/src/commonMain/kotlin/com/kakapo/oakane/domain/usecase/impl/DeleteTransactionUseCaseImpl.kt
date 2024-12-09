package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.oakane.model.category.CategoryLimitModel
import com.kakapo.oakane.model.transaction.TransactionModel

class DeleteTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository
) : DeleteTransactionUseCase {

    override suspend fun execute(transaction: TransactionModel): Result<Unit> = runCatching {
        transactionRepository.deleteTransactionBy(transaction.id)
        val monthlyBudgetId = getMonthlyBudgetId() ?: return@runCatching
        val categoryId = transaction.category.id
        val categoryLimit = getCategoryLimit(categoryId, monthlyBudgetId) ?: return@runCatching
        val spentAmount = categoryLimit.spent - transaction.amount
        categoryLimitRepository.update(spentAmount, categoryLimit.id)

    }

    private suspend fun getMonthlyBudgetId(): Long? {
        return monthlyBudgetRepository.loadActiveMonthlyBudget().getOrNull()
    }

    private suspend fun getCategoryLimit(
        categoryId: Long,
        monthlyBudgetId: Long
    ): CategoryLimitModel? {
        val result = categoryLimitRepository.loadCategoryLimitBy(categoryId, monthlyBudgetId)
            .getOrNull()
        return result
    }
}