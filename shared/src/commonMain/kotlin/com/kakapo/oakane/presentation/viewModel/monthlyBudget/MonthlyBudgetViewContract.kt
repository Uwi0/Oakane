package com.kakapo.oakane.presentation.viewModel.monthlyBudget

data class MonthlyBudgetState(
    val amount: String = ""
)

sealed class MonthlyBudgetEvent {
    data class Changed(val amount: String): MonthlyBudgetEvent()
}