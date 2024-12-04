package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import com.kakapo.oakane.common.asDouble
import com.kakapo.oakane.common.getEndOfMonthUnixTime
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import kotlinx.datetime.Clock

data class MonthlyBudgetState(
    val amount: String = ""
){
    fun asMonthlyBudgetParam(): MonthlyBudgetParam {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return MonthlyBudgetParam(
            totalBudget = amount.asDouble(),
            spentAmount = 0.0,
            startDate = currentTime,
            endDate = getEndOfMonthUnixTime(),
            createdAt = currentTime,
            updatedAt = currentTime
        )
    }
}

sealed class MonthlyBudgetEffect {
    data object NavigateBack: MonthlyBudgetEffect()
}

sealed class MonthlyBudgetEvent {
    data object NavigateBack: MonthlyBudgetEvent()
    data class Changed(val amount: String): MonthlyBudgetEvent()
    data object Save: MonthlyBudgetEvent()
}