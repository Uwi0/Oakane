package com.kakapo.oakane.data.database.model

import com.kakapo.MonthlyBudgetTable
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyBudgetEntity(
    val id: Long = 0,
    val totalBudget: Double = 0.0,
    val spentAmount: Double = 0.0,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
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
