package com.kakapo.data.model

import com.kakapo.database.model.CategoryLimitEntity
import com.kakapo.model.category.CategoryLimitModel

data class CategoryLimitParam(
    val id: Long = 0,
    val categoryId: Long,
    val monthlyBudgetId: Long,
    val limitAmount: Double,
    val spentAmount: Double = 0.0,
) {

    fun toEntity() = CategoryLimitEntity(
        id = id,
        categoryId = categoryId,
        monthlyBudgetId = monthlyBudgetId,
        limitAmount = limitAmount,
        spentAmount = 0.0,
    )
}

fun CategoryLimitEntity.toCategoryLimitModel() = CategoryLimitModel(
    id = id,
    category = categoryEntity.toCategoryModel(),
    limit = limitAmount,
    spent = spentAmount
)
