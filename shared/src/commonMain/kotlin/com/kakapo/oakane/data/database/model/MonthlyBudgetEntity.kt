package com.kakapo.oakane.data.database.model

import com.kakapo.MonthlyBudgetTable

data class MonthlyBudgetEntity(
    val id: Long = 0,
    val totalBudget: Double,
    val spentAmount: Double,
    val startDate: Long,
    val endDate: Long,
    val createdAt: Long,
    val updatedAt: Long
)

fun MonthlyBudgetTable.toMonthlyBudgetEntity() = MonthlyBudgetEntity(
    id = id,
    totalBudget = totalBudget,
    spentAmount = spentAmount,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)
