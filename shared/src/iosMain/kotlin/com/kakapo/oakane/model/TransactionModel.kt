package com.kakapo.oakane.model

import com.kakapo.oakane.model.transaction.TransactionModel

fun List<TransactionModel>.swiftTake(n: Int): List<TransactionModel> {
    return this.take(n)
}