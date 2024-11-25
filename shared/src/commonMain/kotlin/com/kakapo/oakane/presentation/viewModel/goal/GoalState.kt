package com.kakapo.oakane.presentation.viewModel.goal

data class GoalState(
    val fileName: String = "",
    val goalName: String = "",
    val saved: Double = 0.0,
    val target: Double = 0.0,
    val startDate: Long = 0L,
    val endDate: Long = 0L,
    val note: String = ""
){
    val progress: Float get() {
        return (saved / target).toFloat()
    }

    val dayLeft: String get() {
        return (endDate - startDate).toString()
    }
}