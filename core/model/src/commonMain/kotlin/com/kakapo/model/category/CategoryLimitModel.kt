package com.kakapo.model.category

import com.kakapo.common.toFormatCurrency

data class CategoryLimitModel(
    val id: Long,
    val category: CategoryModel,
    val limit: Double,
    val spent: Double,
){
    val name: String get() = category.name
    val isDefault: Boolean get() = category.isDefault
    val fileName: String get() = category.icon
    val formattedColor: Long get() = category.formattedColor
    val iconName: CategoryIconName get() = category.iconName
    val progress: Float get() = if(spent == 0.0) 0F else spent.toFloat() / limit.toFloat()
    val formattedLimit: String get() = limit.toFormatCurrency()
    val formattedSpent: String get() = spent.toFormatCurrency()

    companion object {
        val EMPTY = CategoryLimitModel(1, category = CategoryModel(), 100_000.0, 50_000.0)
    }
}
