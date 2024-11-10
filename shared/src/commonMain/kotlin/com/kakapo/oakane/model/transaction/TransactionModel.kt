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
    val formattedDate: String
        get() {
            return dateCreated.formatDateWith(pattern = TransactionModel.DATE_FORMAT)
        }

    companion object {
        const val DATE_FORMAT = "dd MMM yyyy"
    }
}

fun dummyValues(): List<TransactionModel> {
    val transaction1 = TransactionModel(
        id = 1,
        title = "Groceries",
        type = TransactionType.Expense, // Let's say 1 represents "Food"
        category = "Supermarket",
        amount = 150.50,
        note = "Weekly grocery shopping"
    )

    val transaction2 = TransactionModel(
        id = 2,
        title = "Rent",
        type = TransactionType.Expense, // Let's say 2 represents "Housing"
        category = "Apartment",
        amount = 1000.00,
        note = "October rent"
    )

    val transaction3 = TransactionModel(
        id = 3,
        title = "Gym Membership",
        type = TransactionType.Expense, // Let's say 3 represents "Health & Fitness"
        category = "Fitness",
        amount = 50.00,
        note = "Monthly membership fee"
    )

    val transaction4 = TransactionModel(
        id = 4,
        title = "Dining Out",
        type = TransactionType.Expense, // Same category as groceries for "Food"
        category = "Restaurant",
        amount = 75.00,
        note = "Dinner with friends"
    )

    return listOf(transaction1, transaction2, transaction3, transaction4)
}

fun dummyValue() = TransactionModel(
    id = 4,
    title = "Dining Out",
    type = TransactionType.Expense, // Same category as groceries for "Food"
    category = "Restaurant",
    amount = 75.00,
    note = "Dinner with friends"
)



