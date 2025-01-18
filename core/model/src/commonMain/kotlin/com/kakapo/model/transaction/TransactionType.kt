package com.kakapo.model.transaction

enum class TransactionType {
    Income,
    Expense
}

fun String.asLong() = TransactionType.entries
    .find { it.name == this }
    ?.ordinal
    ?.toLong() ?: 0

fun Long.asTransactionType(): TransactionType {
    return if (this.toInt() == TransactionType.Expense.ordinal) {
        TransactionType.Expense
    } else {
        TransactionType.Income
    }
}

fun Int.asTransactionType(): TransactionType {
    return if (this == TransactionType.Expense.ordinal) {
        TransactionType.Expense
    } else {
        TransactionType.Income
    }
}

fun String.asTransactionType(): TransactionType {
    return when (this) {
        TransactionType.Income.name -> TransactionType.Income
        TransactionType.Expense.name -> TransactionType.Expense
        else -> TransactionType.Income
    }
}
