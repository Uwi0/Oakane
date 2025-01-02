package com.kakapo.model.category

import com.kakapo.common.toColorInt
import com.kakapo.model.transaction.TransactionType

data class CategoryModel(
    val id: Long = 0,
    val name: String = "Salary",
    val type: TransactionType = TransactionType.Income,
    val icon: String = "Salary",
    val color: String = "0xFF4CAF50",
    val isDefault: Boolean = true
) {

    val formattedColor: Int get() = color.toColorInt()

    val iconName: CategoryIconName
        get() {
            return if (isDefault) CategoryIconName.fromString(icon)
            else CategoryIconName.SALARY
        }
}

val defaultCategoryModel = CategoryModel(
    id = 1,
    name = "Salary",
    type = TransactionType.Income,
    icon = "Salary",
    color = "0xFF4CAF50",
)
