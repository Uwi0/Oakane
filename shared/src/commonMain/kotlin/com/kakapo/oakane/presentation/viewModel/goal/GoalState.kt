package com.kakapo.oakane.presentation.viewModel.goal

import com.kakapo.oakane.model.GoalModel

data class GoalState(
    val goal: GoalModel = GoalModel()
){
    val progress: Float get() {
        return (goal.savedMoney / goal.amount).toFloat()
    }

    val dayLeft: String get() {
        return (goal.endDate - goal.startDate).toString()
    }
}