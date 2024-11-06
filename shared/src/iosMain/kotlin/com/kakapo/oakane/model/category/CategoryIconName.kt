package com.kakapo.oakane.model.category

val parentCategories = categoryMap.keys.toList()

fun categoriesName(parentCategory: ParentCategory): List<CategoryIconName> {
    return categoryMap[parentCategory] ?: emptyList()
}