package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.asTransactionType

fun CategoryEntity.toModel() = CategoryModel(
    id = id,
    name = name,
    type = type.asTransactionType(),
    icon = icon,
    isDefault = isDefault,
    color = color.toColorInt()
)

fun String.toColorInt(): Int {
    return if (this.startsWith("0x")) {
        this.removePrefix("0x").toLong(16).toInt()
    } else {
        this.toLong(16).toInt()
    }
}