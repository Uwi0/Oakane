package com.kakapo.data.model

import com.kakapo.database.model.MonthlyBudgetEntity
import com.kakapo.model.monthlyBudget.MonthlyBudgetModel

data class MonthlyBudgetParam(
    val id: Long = 0,
    val totalBudget: Double = 0.0,
    val spentAmount: Double = 0.0,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
){

    fun toEntity() = MonthlyBudgetEntity(
        id = id,
        totalBudget = totalBudget,
        spentAmount = spentAmount,
        startDate = startDate,
        endDate = endDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun MonthlyBudgetEntity.toMonthlyBudgetModel() = MonthlyBudgetModel(
    id = id,
    totalBudget = totalBudget
)