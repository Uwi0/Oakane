package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel

interface GetMonthlyBudgetOverviewUseCase {
    suspend fun execute(): Result<MonthlyBudgetOverViewModel>
}