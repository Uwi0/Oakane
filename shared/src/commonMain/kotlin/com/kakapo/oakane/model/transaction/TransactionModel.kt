package com.kakapo.oakane.model.transaction

import com.kakapo.oakane.common.formatDateWith

data class TransactionModel(
    val id: Long = 0,
    val title: String = "",
    val type: TransactionType = TransactionType.Expense,
    val category: String = "",
    val dateCreated: Long = 1729214280000,
    val amount: Double = 0.0,
    val note: String = ""
) {
    val formattedDate: String get() {
        return dateCreated.formatDateWith(pattern = TransactionModel.DATE_FORMAT)
    }
    companion object {
        const val DATE_FORMAT = "dd MMM yyyy"
    }
}

fun dummyValue() = TransactionModel(
    id = 4,
    title = "Dining Out",
    type = TransactionType.Expense, // Same category as groceries for "Food"
    category = "Restaurant",
    amount = 75.00,
    note = "Dinner with friends"
)



