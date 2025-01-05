package com.kakapo.domain.usecase.impl

import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType

class DeleteTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val walletRepository: WalletRepository
) : DeleteTransactionUseCase {

    override suspend fun execute(transaction: TransactionModel): Result<Unit> = runCatching {
        transactionRepository.deleteTransactionBy(transaction.id)
        deleteWallet(transaction)
        val monthlyBudgetId = getMonthlyBudgetId() ?: return@runCatching
        val categoryId = transaction.category.id
        val categoryLimit = getCategoryLimit(categoryId, monthlyBudgetId) ?: return@runCatching
        val spentAmount = categoryLimit.spent - transaction.amount
        categoryLimitRepository.update(spentAmount, categoryLimit.id)
    }

    private suspend fun deleteWallet(transaction: TransactionModel) {
        val amount = transaction.amount
        val balance = if (transaction.type == TransactionType.Income) -amount else amount
        walletRepository.update(balance).getOrNull()
    }

    private suspend fun getMonthlyBudgetId(): Long? {
        return monthlyBudgetRepository.loadActiveMonthlyBudget().getOrNull()
    }

    private suspend fun getCategoryLimit(
        categoryId: Long,
        monthlyBudgetId: Long
    ): CategoryLimitModel? {
        return categoryLimitRepository.loadCategoryLimitBy(
            categoryId,
            monthlyBudgetId
        ).getOrNull()
    }
}