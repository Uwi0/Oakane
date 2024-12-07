package com.kakapo.oakane.data.database.model

import com.kakapo.CategoryTable

data class CategoryEntity(
    val id: Long = 0,
    val name: String = "",
    val type: Long = 0,
    val icon: String = "",
    val color: String = "",
    val isDefault: Long = 0
)

fun CategoryTable.toCategoryEntity() = CategoryEntity(
    id = id,
    name = name,
    type = type,
    icon = icon,
    color = color,
    isDefault = isDefault
)
