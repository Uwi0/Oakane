package com.kakapo.model.monthlyBudget

data class MonthlyBudgetOverViewModel(
    val limit: Double = 5_000_000.0,
    val spent: Double = 1_000_000.0,
    val left: Double = 4_000_000.0,
    val totalIncome: Double = 5_000_000.0,
    val totalExpense: Double = 1_000_000.0,
    val progress: Float = 0.2f
)

val defaultMonthlyBudgetOverView = MonthlyBudgetOverViewModel()
