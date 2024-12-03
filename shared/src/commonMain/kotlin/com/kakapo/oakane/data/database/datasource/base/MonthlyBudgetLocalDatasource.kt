package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.MonthlyBudgetEntity

interface MonthlyBudgetLocalDatasource {
    suspend fun insertMonthlyBudget(entity: MonthlyBudgetEntity): Result<Unit>
}