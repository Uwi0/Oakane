package com.kakapo.oakane.model.category

import com.kakapo.oakane.model.transaction.TransactionType

data class CategoryModel(
    val id: Long,
    val name: String,
    val type: TransactionType,
    val icon: String,
    val isDefault: Long
)
