package com.kakapo.model

data class GoalModel(
    val id: Long = 0,
    val amount: Double = 0.0,
    val goalName: String = "My Goal",
    val startDate: Long = 0,
    val endDate: Long = 0,
    val fileName: String = "",
    val savedMoney: Double = 0.0,
    val note: String = "some note",
    val currency: Currency = Currency.IDR
) {

    val progress: Float
        get() {
            return if (savedMoney != 0.0) (savedMoney / amount).toFloat()
            else 0.0f
        }
}
