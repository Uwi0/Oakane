package com.kakapo.model

import com.kakapo.model.transaction.TransactionModel

fun List<TransactionModel>.swiftTake(n: Int): List<TransactionModel> {
    return this.take(n)
}