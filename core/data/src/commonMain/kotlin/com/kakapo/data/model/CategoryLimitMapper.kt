package com.kakapo.data.model

import com.kakapo.database.model.CategoryLimitEntity
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryLimitModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
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

fun List<CategoryLimitModel>.toCategoryLimitParamEncodedString(): String {
    val params =  this.map { model ->
        CategoryLimitParam(
            categoryId = model.category.id,
            monthlyBudgetId = 0,
            limitAmount = model.limit,
        )
    }
    return Json.encodeToString(params)
}

fun CategoryLimitEntity.toCategoryLimitModel(currency: Currency) = CategoryLimitModel(
    id = id,
    category = categoryEntity.toCategoryModel(),
    limit = limitAmount,
    spent = spentAmount,
    currency = currency,
)
