package com.kakapo.data.model

import com.kakapo.common.asLong
import com.kakapo.database.model.CategoryEntity
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.transaction.asTransactionType

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

