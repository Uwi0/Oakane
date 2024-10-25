package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.CategoryEntity
import com.kakapo.oakane.model.category.CategoryModel

fun CategoryEntity.toModel() = CategoryModel(
    id = id,
    name = name,
    type = type,
    icon = icon,
    isDefault = isDefault
)