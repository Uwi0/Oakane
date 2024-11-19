package com.kakapo.oakane.model

data class GoalModel(
    val id: String = "",
    val amount: Double = 0.0,
    val goalName: String = "My Goal",
    val startDate: Long = 0,
    val endDate: Long = 0,
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
