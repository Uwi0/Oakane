package com.kakapo.oakane.model.transaction

enum class TransactionType {
    Expense,
    Income
}

fun String.asLong() = TransactionType.entries
    .find { it.name == this }
    ?.ordinal
    ?.toLong() ?: 0

fun Long.asTransactionType(): TransactionType {
    return if (this.toInt() == TransactionType.Expense.ordinal){
        TransactionType.Expense
    } else {
        TransactionType.Income
    }
}
