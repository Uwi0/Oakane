package com.kakapo.oakane.model

data class TransactionModel(
    val id: String = "",
    val title: String = "",
    val category: Int = 0,
    val tag: String = "",
    val date: String = "",
    val amount: Double = 0.0,
    val note: String = ""
)

fun dummyTransaction(): List<TransactionModel> {
    val transaction1 = TransactionModel(
        id = "1",
        title = "Groceries",
        category = 2, // Let's say 1 represents "Food"
        tag = "Supermarket",
        date = "2024-10-01",
        amount = 150.50,
        note = "Weekly grocery shopping"
    )

    val transaction2 = TransactionModel(
        id = "2",
        title = "Rent",
        category = 2, // Let's say 2 represents "Housing"
        tag = "Apartment",
        date = "2024-10-02",
        amount = 1000.00,
        note = "October rent"
    )

    val transaction3 = TransactionModel(
        id = "3",
        title = "Gym Membership",
        category = 2, // Let's say 3 represents "Health & Fitness"
        tag = "Fitness",
        date = "2024-10-03",
        amount = 50.00,
        note = "Monthly membership fee"
    )

    val transaction4 = TransactionModel(
        id = "4",
        title = "Dining Out",
        category = 2, // Same category as groceries for "Food"
        tag = "Restaurant",
        date = "2024-10-04",
        amount = 75.00,
        note = "Dinner with friends"
    )

    return listOf(transaction1, transaction2, transaction3, transaction4)
}

