package com.kakapo.data.repository.impl

import com.kakapo.data.repository.base.BackupRepository
import com.kakapo.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.database.datasource.base.CategoryLocalDatasource
import com.kakapo.database.datasource.base.GoalLocalDatasource
import com.kakapo.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.database.model.CategoryEntity
import com.kakapo.database.model.CategoryLimitEntity
import com.kakapo.database.model.GoalEntity
import com.kakapo.database.model.MonthlyBudgetEntity
import com.kakapo.database.model.TransactionEntity
import com.kakapo.database.model.WalletEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class BackupRepositoryImpl(
    private val categoryLimitDatasource: CategoryLimitLocalDatasource,
    private val categoryDatasource: CategoryLocalDatasource,
    private val goalDatasource: GoalLocalDatasource,
    private val monthlyDatasource: MonthlyBudgetLocalDatasource,
    private val transactionDatasource: TransactionLocalDatasource,
    private val walletDatasource: WalletLocalDatasource
): BackupRepository {

    override suspend fun createBackup(): Result<String> = coroutineScope {
        runCatching {
            val categoryLimits = async { categoryLimitDatasource.getCategoryLimitsForBackup()}
            val categories = async { categoryDatasource.getCategoriesForBackup() }
            val goals = async { goalDatasource.getGoalsForBackup() }
            val monthlyBudgets = async { monthlyDatasource.getMonthlyBudgetsForBackup() }
            val transactions = async { transactionDatasource.getTransactionsForBackup() }
            val wallets = async { walletDatasource.getWalletForBackup() }

            val backup = BackupModel(
                categoryLimits.await().getOrElse { emptyList() },
                categories.await().getOrElse { emptyList() },
                goals.await().getOrElse { emptyList() },
                monthlyBudgets.await().getOrElse { emptyList() },
                transactions.await().getOrElse { emptyList() },
                wallets.await().getOrElse { emptyList() }
            )

            Json.encodeToString(backup)
        }
    }

    override suspend fun restoreBackup(backup: String): Result<Unit> = coroutineScope {
        runCatching {
            val backupModel = Json.decodeFromString(BackupModel.serializer(), backup)

            val categoryDeferred = async {
                categoryDatasource.restoreCategories(backupModel.categories)
            }
            val categoryLimitDeferred = async {
                categoryLimitDatasource.restoreCategoryLimits(backupModel.categoryLimits)
            }
            val goalDeferred = async {
                goalDatasource.restoreGoals(backupModel.goals)
            }
            val monthlyBudgetDeferred = async {
                monthlyDatasource.restoreMonthlyBudgets(backupModel.monthlyBudgets)
            }
            val transactionDeferred = async {
                transactionDatasource.restoreTransactions(backupModel.transactions)
            }
            val walletDeferred = async {
                walletDatasource.restoreWallets(backupModel.wallets)
            }

            categoryDeferred.await()
            categoryLimitDeferred.await()
            goalDeferred.await()
            monthlyBudgetDeferred.await()
            transactionDeferred.await()
            walletDeferred.await()
            Unit
        }
    }

    @Serializable
    data class BackupModel(
        val categoryLimits: List<CategoryLimitEntity>,
        val categories: List<CategoryEntity>,
        val goals: List<GoalEntity>,
        val monthlyBudgets: List<MonthlyBudgetEntity>,
        val transactions: List<TransactionEntity>,
        val wallets: List<WalletEntity>
    )

}