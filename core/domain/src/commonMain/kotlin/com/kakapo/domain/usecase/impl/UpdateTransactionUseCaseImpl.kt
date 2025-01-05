package com.kakapo.domain.usecase.impl

import com.kakapo.data.model.TransactionParam
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.model.category.CategoryLimitModel

class UpdateTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val walletRepository: WalletRepository
) : UpdateTransactionUseCase {

    override suspend fun executed(
        transaction: TransactionParam,
        spentAmountBefore: Double
    ): Result<Unit> = runCatching {
        updateWallet(transaction, spentAmountBefore)
        transactionRepository.update(transaction).getOrThrow()
        val monthlyId = getMonthlyBudgetId() ?: return@runCatching
        val categoryLimit = getCategoryLimit(monthlyId, transaction.category.id) ?: return@runCatching
        val spentAmount = (categoryLimit.spent - spentAmountBefore) + transaction.amount
        categoryLimitRepository.update(spentAmount, categoryLimit.id)
    }

    private suspend fun updateWallet(transaction: TransactionParam, spentAmountBefore: Double) {
        val balanceBefore = if (transaction.type == 0L) -spentAmountBefore else spentAmountBefore
        val balanceAfter = if (transaction.type == 0L) transaction.amount else -transaction.amount
        walletRepository.update(balanceBefore, transaction.walletId).getOrNull()
        walletRepository.update(balanceAfter, transaction.walletId).getOrNull()
    }

    private suspend fun getMonthlyBudgetId(): Long? {
        return monthlyBudgetRepository.loadActiveMonthlyBudget().getOrNull()
    }

    private suspend fun getCategoryLimit(monthlyId: Long, categoryId: Long): CategoryLimitModel? {
        return categoryLimitRepository.loadCategoryLimitBy(categoryId, monthlyId).getOrNull()
    }

}