package com.kakapo.oakane.model.category

import com.kakapo.oakane.data.model.toColorInt
import com.kakapo.oakane.model.transaction.TransactionType

data class CategoryModel(
    val id: Long,
    val name: String,
    val type: TransactionType,
    val icon: String,
    val color: String,
    val isDefault: Long
) {
    val formattedColor: Int get() {
        return color.toColorInt()
    }
}
