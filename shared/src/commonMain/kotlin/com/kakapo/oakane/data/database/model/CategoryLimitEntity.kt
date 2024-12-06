package com.kakapo.oakane.data.database.model

data class CategoryLimitEntity(
    val id: Long,
    val categoryId: Long,
    val monthlyBudgetId: Long,
    val limitAmount: Double,
    val spentAmount: Double,
    val createdAt: Long,
    val updatedAt: Long
)
