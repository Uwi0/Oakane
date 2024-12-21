package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GetMonthlyBudgetOverviewUseCase {
    @NativeCoroutines
    suspend fun execute(): Result<MonthlyBudgetOverViewModel>
}