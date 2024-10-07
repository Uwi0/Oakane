package com.kakapo.oakane.model

data class GoalModel(
    val id: String = "",
    val amount: Double = 0.0,
    val goalName: String = "My Goal",
    val deadline: String = "12-03-2025",
    val fileName: String = "",
    val savedMoney: Double = 0.0,
    val note: String = "some note"
) {

    val progress: Float
        get() {
            return if (savedMoney != 0.0) (savedMoney / amount).toFloat()
            else 0.0f
        }
}

fun dummyGoals(): List<GoalModel> {
    return listOf(
        GoalModel(
            id = "1",
            amount = 5000.0,
            goalName = "Vacation Trip",
            deadline = "12-12-2024",
            fileName = "vacation.jpg",
            savedMoney = 2500.0,
            note = "Saving for a trip to Bali"
        ),
        GoalModel(
            id = "2",
            amount = 20000.0,
            goalName = "Car Purchase",
            deadline = "01-01-2026",
            fileName = "car.png",
            savedMoney = 5000.0,
            note = "Saving for a new car"
        ),
        GoalModel(
            id = "3",
            amount = 1500.0,
            goalName = "New Laptop",
            deadline = "11-30-2024",
            fileName = "laptop.png",
            savedMoney = 750.0,
            note = "Upgrade my laptop"
        ),
        GoalModel(
            id = "4",
            amount = 10000.0,
            goalName = "Emergency Fund",
            deadline = "05-01-2025",
            fileName = "emergency_fund.png",
            savedMoney = 3000.0,
            note = "Emergency savings"
        )
    )
}

fun dummyGoal(): GoalModel {
    return GoalModel(
        id = "4",
        amount = 10000.0,
        goalName = "Emergency Fund",
        deadline = "05-01-2025",
        fileName = "emergency_fund.png",
        savedMoney = 3000.0,
        note = "Emergency savings"
    )
}
