package com.kakapo.oakane.domain.usecase.impl

import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.repository.base.WalletRepository
import com.kakapo.oakane.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.oakane.model.category.CategoryLimitModel

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
        Logger.d("wallet_id: ${transaction.walletId}")
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