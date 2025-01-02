package com.kakapo.database.model

import com.kakapo.CategoryLimitTable
import com.kakapo.GetCategoryLimits
import kotlinx.serialization.Serializable

@Serializable
data class CategoryLimitEntity(
    val id: Long = 0,
    val categoryId: Long,
    val monthlyBudgetId: Long = 0,
    val limitAmount: Double,
    val spentAmount: Double = 0.0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val categoryEntity: CategoryEntity = CategoryEntity()
)

fun GetCategoryLimits.toCategoryLimitEntity(): CategoryLimitEntity {
    val categoryEntity = CategoryEntity(
        id = categoryId,
        name = categoryName,
        icon = categoryIcon,
        color = categoryColor,
        isDefault = categoryIsDefault
    )
    return CategoryLimitEntity(
        id = limitId,
        categoryId = categoryId,
        limitAmount = limitAmount,
        categoryEntity = categoryEntity,
        spentAmount = spentAmount
    )
}

fun CategoryLimitTable.toCategoryLimitEntity(): CategoryLimitEntity {
    return CategoryLimitEntity(
        id = id,
        categoryId = categoryId,
        monthlyBudgetId = monthlyBudgetId,
        limitAmount = limitAmount,
        spentAmount = spentAmount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
