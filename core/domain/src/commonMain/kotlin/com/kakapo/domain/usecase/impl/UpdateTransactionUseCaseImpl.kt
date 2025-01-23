package com.kakapo.domain.usecase.impl

import co.touchlab.kermit.Logger
import com.kakapo.data.model.TransactionParam
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType

class UpdateTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val walletRepository: WalletRepository
) : UpdateTransactionUseCase {

    override suspend fun executed(
        transaction: TransactionParam,
        transactionBefore: TransactionModel
    ): Result<Unit> = runCatching {
        updateCurrentWallet(transaction)
        updateOldWallet(transactionBefore)
        transactionRepository.update(transaction).getOrThrow()
        Logger.d("Transaction updated: $transactionBefore, $transaction")
        val monthlyId = getMonthlyBudgetId() ?: return@runCatching
        val categoryLimit = getCategoryLimit(monthlyId, transaction.category.id) ?: return@runCatching
        val spentAmount = (categoryLimit.spent - transactionBefore.amount) + transaction.amount
        categoryLimitRepository.update(spentAmount, categoryLimit.id)
    }

    private suspend fun updateCurrentWallet(transaction: TransactionParam) {
        val balanceAfter = if (transaction.type == 0L) transaction.amount else -transaction.amount
        walletRepository.update(balanceAfter, transaction.walletId).getOrNull()
    }

    private suspend fun updateOldWallet(transactionBefore: TransactionModel) {
        val balanceBefore = if (transactionBefore.type == TransactionType.Income) -transactionBefore.amount else transactionBefore.amount
        walletRepository.update(balanceBefore, transactionBefore.walletId).getOrNull()
    }

    private suspend fun getMonthlyBudgetId(): Long? {
        return monthlyBudgetRepository.loadActiveMonthlyBudget().getOrNull()
    }

    private suspend fun getCategoryLimit(monthlyId: Long, categoryId: Long): CategoryLimitModel? {
        return categoryLimitRepository.loadCategoryLimitBy(categoryId, monthlyId).getOrNull()
    }

}