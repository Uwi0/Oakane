package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.asTransactionType

fun CategoryEntity.toModel() = CategoryModel(
    id = id,
    name = name,
    type = type.asTransactionType(),
    icon = icon,
    isDefault = isDefault == 1L,
    color = color
)

