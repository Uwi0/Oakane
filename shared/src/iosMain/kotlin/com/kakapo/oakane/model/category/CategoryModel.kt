package com.kakapo.oakane.model.category

import com.kakapo.oakane.model.transaction.TransactionType

fun List<CategoryModel>.swiftFilterBy(type: TransactionType): List<CategoryModel> {
    return this.filter { it.type == type }
}
