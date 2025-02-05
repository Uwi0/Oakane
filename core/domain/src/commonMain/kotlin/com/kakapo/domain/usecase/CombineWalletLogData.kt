package com.kakapo.domain.usecase

import com.kakapo.model.transaction.TransactionType
import com.kakapo.model.wallet.WalletLogItem

fun List<WalletLogItem<*>>.toExpenseAndIncome(): Pair<Double, Double> {

    fun filterAndSum(predicate: (TransactionType) -> Boolean) =
        filter { predicate(it.type) }
            .sumOf { kotlin.math.abs(it.amount) }

    val expense = filterAndSum { it == TransactionType.Expense }
    val income = filterAndSum { it == TransactionType.Income }

    return Pair(expense, income)
}