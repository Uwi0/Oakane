package com.kakapo.oakane.data.database.model

import com.kakapo.CategoryTable

data class CategoryEntity(
    val id: Long,
    val name: String,
    val type: Long,
    val icon: String,
    val color: String,
    val isDefault: Long
)

fun CategoryTable.toCategoryEntity() = CategoryEntity(
    id = id,
    name = name,
    type = type,
    icon = icon,
    color = color,
    isDefault = isDefault
)
