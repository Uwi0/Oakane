package com.kakapo.oakane.model.category

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.transaction.TransactionType

data class CategoryModel(
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val icon: String,
    val color: String,
    val isDefault: Boolean
) {

    val iconName: CategoryIconName get() {
        return if (isDefault) CategoryIconName.fromString(icon)
        else CategoryIconName.SALARY
    }
}
