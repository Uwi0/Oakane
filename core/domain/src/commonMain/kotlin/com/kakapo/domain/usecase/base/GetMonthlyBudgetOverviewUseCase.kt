package com.kakapo.domain.usecase.base

import com.kakapo.common.startDateAndEndDateOfMonth
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface GetMonthlyBudgetOverviewUseCase {
    @NativeCoroutines
    suspend fun execute(
        walletId: Long? = null,
        startDateOfMont: Long = startDateAndEndDateOfMonth().first,
        endDateOfMonth: Long = startDateAndEndDateOfMonth().second
    ): Result<MonthlyBudgetOverView>
}