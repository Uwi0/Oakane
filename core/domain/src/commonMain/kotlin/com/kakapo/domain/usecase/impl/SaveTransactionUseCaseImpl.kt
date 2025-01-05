package com.kakapo.domain.usecase.impl

import com.kakapo.data.model.TransactionParam
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.SaveTransactionUseCase

class SaveTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val walletRepository: WalletRepository
) : SaveTransactionUseCase {

    override suspend fun execute(transaction: TransactionParam): Result<Unit> = runCatching {
        val walletId = saveWallet(transaction) ?: return@runCatching
        transactionRepository.save(transaction.copy(walletId = walletId)).getOrThrow()
        val monthlyBudgetId = getMonthlyBudgetId() ?: return@runCatching
        val categoryId = transaction.category.id
        val categoryLimitId = getCategoryLimit(categoryId, monthlyBudgetId) ?: return@runCatching
        categoryLimitRepository.updateIncrement(transaction.amount, categoryLimitId).getOrNull()
    }

    private suspend fun saveWallet(transaction: TransactionParam): Long? {
        val balance = transaction.saveBalance
        walletRepository.update(balance).getOrNull()
        return walletRepository.loadWalletId().getOrNull()
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