package com.kakapo.model.category

import com.kakapo.model.transaction.TransactionType

fun List<CategoryModel>.swiftFilterBy(type: TransactionType): List<CategoryModel> {
    return this.filter { it.type == type }
}
