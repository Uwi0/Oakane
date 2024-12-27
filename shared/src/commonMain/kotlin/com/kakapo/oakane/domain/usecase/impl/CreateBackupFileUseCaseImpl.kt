package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.data.repository.base.GoalRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.repository.base.WalletRepository
import com.kakapo.oakane.domain.usecase.base.CreateBackupFileUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class CreateBackupFileUseCaseImpl(
    private val categoryLimitRepository: CategoryLimitRepository,
    private val categoryRepository: CategoryRepository,
    private val goalRepository: GoalRepository,
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
): CreateBackupFileUseCase {

    override suspend fun createBackupFile(): Result<String> = coroutineScope {
        runCatching {
            val categoryLimitDeferred = async { categoryLimitRepository.loadCategoryLimitForBackup() }
            val categoryDeferred = async { categoryRepository.loadCategoryForBackup() }
            val goalDeferred = async { goalRepository.loadGoalForBackup() }
            val monthlyBudgetDeferred = async { monthlyBudgetRepository.loadMonthlyBudgetForBackup() }
            val transactionDeferred = async { transactionRepository.loadTransactionForBackup() }
            val walletDeferred = async { walletRepository.loadWalletForBackup() }

            val categoryLimit = categoryLimitDeferred.await().getOrNull() ?: ""
            val category = categoryDeferred.await().getOrNull() ?: ""
            val goal = goalDeferred.await().getOrNull() ?: ""
            val monthlyBudget = monthlyBudgetDeferred.await().getOrNull() ?: ""
            val transaction = transactionDeferred.await().getOrNull() ?: ""
            val wallet = walletDeferred.await().getOrNull() ?: ""

            val backup = BackupHolder(
                categoryLimit = categoryLimit,
                category = category,
                goal = goal,
                monthlyBudget = monthlyBudget,
                transaction = transaction,
                wallet = wallet
            )

            Json.encodeToString(backup)
        }
    }

    @Serializable
    data class BackupHolder(
        val categoryLimit: String,
        val category: String,
        val goal: String,
        val monthlyBudget: String,
        val transaction: String,
        val wallet: String
    )
}