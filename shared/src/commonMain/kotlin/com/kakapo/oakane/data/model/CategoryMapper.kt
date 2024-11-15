package com.kakapo.oakane.data.model

import com.kakapo.oakane.common.asLong
import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.asTransactionType

fun CategoryEntity.toCategoryModel() = CategoryModel(
    id = id,
    name = name,
    type = type.asTransactionType(),
    icon = icon,
    isDefault = isDefault == 1L,
    color = color
)

fun CategoryModel.toCategoryEntity() = CategoryEntity(
    id = id,
    name = name,
    type = type.ordinal.toLong(),
    icon = icon,
    color = color,
    isDefault = isDefault.asLong()
)

