package com.kakapo.oakane.domain.usecase.base

import com.kakapo.common.startDateAndEndDateOfMonth
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GetMonthlyBudgetOverviewUseCase {
    @NativeCoroutines
    suspend fun execute(
        walletId: Long? = null,
        startDateOfMont: Long = startDateAndEndDateOfMonth().first,
        endDateOfMonth: Long = startDateAndEndDateOfMonth().second
    ): Result<MonthlyBudgetOverViewModel>
}