package com.kakapo.oakane.model.monthlyBudget

data class MonthlyBudgetOverViewModel(
    val limit: Double = 0.0,
    val spent: Double = 0.0,
    val left: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val progress: Float = 0f
)

val defaultMonthlyBudgetOverView = MonthlyBudgetOverViewModel()
