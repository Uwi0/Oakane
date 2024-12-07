package com.kakapo.oakane.data.database.model

data class CategoryLimitEntity(
    val id: Long = 0,
    val categoryId: Long,
    val monthlyBudgetId: Long,
    val limitAmount: Double,
    val spentAmount: Double,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)
