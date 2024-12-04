package com.kakapo.oakane.data.database.datasource.impl

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.oakane.data.database.datasource.base.MonthlyBudgetLocalDatasource
import com.kakapo.oakane.data.database.model.MonthlyBudgetEntity
import com.kakapo.oakane.data.database.model.toMonthlyBudgetEntity

class MonthlyBudgetLocalDatasourceImpl(sqlDriver: SqlDriver): MonthlyBudgetLocalDatasource {

    private val monthlyBudgetTable = Database(sqlDriver).monthlyBudgetEntityQueries

    override suspend fun insertMonthlyBudget(entity: MonthlyBudgetEntity): Result<Unit> {
        return runCatching {
            monthlyBudgetTable.insertMonthlyBudget(
                totalBudget = entity.totalBudget,
                spentAmount = entity.spentAmount,
                startDate = entity.startDate,
                endDate = entity.endDate,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }

    override suspend fun tableIsNotEmpty(): Result<Boolean> {
        return runCatching {
            monthlyBudgetTable.countRows().executeAsOne() != 0L
        }
    }

    override suspend fun getMonthlyBudget(): Result<MonthlyBudgetEntity> {
        return runCatching {
            monthlyBudgetTable.getMonthlyBudget()
                .executeAsOne()
                .toMonthlyBudgetEntity()
        }
    }
}