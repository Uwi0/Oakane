package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.MonthlyBudgetEntity

data class MonthlyBudgetParam(
    val totalBudget: Double,
    val spentAmount: Double,
    val startDate: Long,
    val endDate: Long,
    val createdAt: Long,
    val updatedAt: Long
){

    fun toEntity() = MonthlyBudgetEntity(
        totalBudget = totalBudget,
        spentAmount = spentAmount,
        startDate = startDate,
        endDate = endDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}