package com.kakapo.model.monthlyBudget

import com.kakapo.model.Currency

data class MonthlyBudgetModel(
    val id: Long,
    val totalBudget: Double,
    val currency: Currency
)
