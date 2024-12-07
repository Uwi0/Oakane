package com.kakapo.oakane.data.database.model

data class CategoryLimitParam(
    val categoryId: Long,
    val monthlyBudgetId: Long,
    val limitAmount: Double,
    val spentAmount: Double,
    val updateAt: Long = 0
){

    fun toEntity() = CategoryLimitEntity(
        categoryId = categoryId,
        monthlyBudgetId = monthlyBudgetId,
        limitAmount = limitAmount,
        spentAmount = 0.0,
    )
}
