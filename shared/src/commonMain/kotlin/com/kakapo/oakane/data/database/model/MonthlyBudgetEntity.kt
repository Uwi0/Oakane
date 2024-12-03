package com.kakapo.oakane.data.database.model

data class MonthlyBudgetEntity(
    val id: Long,
    val totalBudget: Double,
    val spentAmount: Double,
    val startDate: Long,
    val endDate: Long,
    val createdAt: Long,
    val updatedAt: Long
)
