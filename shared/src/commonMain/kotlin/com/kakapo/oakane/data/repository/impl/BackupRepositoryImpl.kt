package com.kakapo.oakane.data.repository.impl

import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.CategoryLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.GoalLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.TransactionLocalDatasource
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.data.database.model.CategoryLimitEntity
import com.kakapo.oakane.data.database.model.GoalEntity
import com.kakapo.oakane.data.database.model.MonthlyBudgetEntity
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.data.repository.base.BackupRepository
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
): BackupRepository {

    override suspend fun createBackup(): Result<String> = coroutineScope {
        runCatching {
            val categoryLimits = async { categoryLimitDatasource.getCategoryLimitsForBackup()}
            val categories = async { categoryDatasource.getCategoriesForBackup() }
            val goals = async { goalDatasource.getGoalsForBackup() }
            val monthlyBudgets = async { monthlyDatasource.getMonthlyBudgetsForBackup() }
            val transactions = async { transactionDatasource.getTransactionsForBackup() }

            val backup = BackupModel(
                categoryLimits.await().getOrElse { emptyList() },
                categories.await().getOrElse { emptyList() },
                goals.await().getOrElse { emptyList() },
                monthlyBudgets.await().getOrElse { emptyList() },
                transactions.await().getOrElse { emptyList() }
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

            categoryDeferred.await()
            categoryLimitDeferred.await()
            goalDeferred.await()
            monthlyBudgetDeferred.await()
            transactionDeferred.await()
            Unit
        }
    }

    @Serializable
    data class BackupModel(
        val categoryLimits: List<CategoryLimitEntity>,
        val categories: List<CategoryEntity>,
        val goals: List<GoalEntity>,
        val monthlyBudgets: List<MonthlyBudgetEntity>,
        val transactions: List<TransactionEntity>
    )

}