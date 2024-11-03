package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.transaction.asTransactionType

fun CategoryEntity.toModel() = CategoryModel(
    id = id,
    name = name,
    type = type.asTransactionType(),
    icon = CategoryIconName.fromString(icon),
    isDefault = isDefault,
    color = color
)

