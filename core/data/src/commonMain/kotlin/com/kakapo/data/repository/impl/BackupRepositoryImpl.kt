package com.kakapo.data.repository.impl

import com.kakapo.data.repository.base.BackupRepository
import com.kakapo.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.database.datasource.base.CategoryLocalDatasource
import com.kakapo.database.datasource.base.GoalLocalDatasource
import com.kakapo.database.datasource.base.GoalSavingsLocalDatasource
import com.kakapo.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.database.datasource.base.TransactionLocalDatasource
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.database.datasource.base.WalletTransferLocalDatasource
import com.kakapo.database.model.CategoryEntity
import com.kakapo.database.model.CategoryLimitEntity
import com.kakapo.database.model.GoalEntity
import com.kakapo.database.model.GoalSavingsEntity
import com.kakapo.database.model.MonthlyBudgetEntity
import com.kakapo.database.model.TransactionEntity
import com.kakapo.database.model.WalletEntity
import com.kakapo.database.model.WalletTransferEntity
import com.kakapo.preference.constant.IntKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class BackupRepositoryImpl(
    private val categoryLimitDatasource: CategoryLimitLocalDatasource,
    private val categoryDatasource: CategoryLocalDatasource,
    private val goalDatasource: GoalLocalDatasource,
    private val goalSavingDatasource: GoalSavingsLocalDatasource,
    private val monthlyDatasource: MonthlyBudgetLocalDatasource,
    private val transactionDatasource: TransactionLocalDatasource,
    private val walletDatasource: WalletLocalDatasource,
    private val walletTransferDatasource: WalletTransferLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource,
    private val dispatcher: CoroutineDispatcher
) : BackupRepository {

    override suspend fun createBackup(): Result<String> = withContext(dispatcher) {
        runCatching {
            val categoryLimits = async { categoryLimitDatasource.getCategoryLimitsForBackup() }
            val categories = async { categoryDatasource.getCategoriesForBackup() }
            val goals = async { goalDatasource.getGoalsForBackup() }
            val goalSavings = async { goalSavingDatasource.getGoalSavingsForBackup() }
            val monthlyBudgets = async { monthlyDatasource.getMonthlyBudgetsForBackup() }
            val transactions = async { transactionDatasource.getTransactionsForBackup() }
            val wallets = async { walletDatasource.getWalletForBackup() }
            val walletTransfers = async { walletTransferDatasource.getWalletTransfersForBackup() }
            val currency = async { preferenceDatasource.getSavedCurrency() }

            val backup = BackupModel(
                currency.await(),
                categoryLimits.await().getOrElse { emptyList() },
                categories.await().getOrElse { emptyList() },
                goals.await().getOrElse { emptyList() },
                goalSavings.await().getOrElse { emptyList() },
                monthlyBudgets.await().getOrElse { emptyList() },
                transactions.await().getOrElse { emptyList() },
                wallets.await().getOrElse { emptyList() },
                walletTransfers.await().getOrElse { emptyList() }
            )

            Json.encodeToString(backup)
        }
    }

    override suspend fun restoreBackup(backup: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val backupModel = Json.decodeFromString(BackupModel.serializer(), backup)

            val currencyDeferred = async {
                preferenceDatasource.saveIntValue(IntKey.CURRENCY, backupModel.currency)
            }
            val categoryDeferred = async {
                categoryDatasource.restoreCategories(backupModel.categories)
            }
            val categoryLimitDeferred = async {
                categoryLimitDatasource.restoreCategoryLimits(backupModel.categoryLimits)
            }
            val goalDeferred = async {
                goalDatasource.restoreGoals(backupModel.goals)
            }
            val goalSavingsDeferred = async {
                goalSavingDatasource.restoreGoalSavings(backupModel.goalSavings)
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
            val walletTransferDeferred = async {
                walletTransferDatasource.restoreWalletTransfer(backupModel.walletTransfers)
            }

            currencyDeferred.await()
            categoryDeferred.await()
            categoryLimitDeferred.await()
            goalDeferred.await()
            goalSavingsDeferred.await()
            monthlyBudgetDeferred.await()
            transactionDeferred.await()
            walletDeferred.await()
            walletTransferDeferred.await()
            Unit
        }
    }

    @Serializable
    data class BackupModel(
        val currency: Int,
        val categoryLimits: List<CategoryLimitEntity>,
        val categories: List<CategoryEntity>,
        val goals: List<GoalEntity>,
        val goalSavings: List<GoalSavingsEntity>,
        val monthlyBudgets: List<MonthlyBudgetEntity>,
        val transactions: List<TransactionEntity>,
        val wallets: List<WalletEntity>,
        val walletTransfers: List<WalletTransferEntity>
    )

}