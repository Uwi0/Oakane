package com.kakapo.oakane.model

fun List<TransactionModel>.swiftTake(n: Int): List<TransactionModel> {
    return this.take(n)
}